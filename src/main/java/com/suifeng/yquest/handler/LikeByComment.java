package com.suifeng.yquest.handler;

import com.alibaba.fastjson.JSON;
import com.suifeng.yquest.api.enums.LikeTypeEnum;
import com.suifeng.yquest.config.context.LoginContextHolder;
import com.suifeng.yquest.config.redis.RedisUtil;
import com.suifeng.yquest.entity.ShareCommentReply;
import com.suifeng.yquest.entity.ShareLikedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class LikeByComment {
    private static final String LIKE_COMMENT_STATUS_KEY = "like:comment:status";
    private static final String LIKE_COMMENT_COUNT_KEY = "like:comment:count";
    private static final String LIKE_REPLY_STATUS_KEY = "like:reply:status";
    private static final String LIKE_REPLY_COUNT_KEY = "like:reply:count";
    private static final String LIKE_CHANGE_COUNT_KEY = "like:change:count";
    private static final Integer Like_THRESHOLD = 3;  //点赞数量达到一定值进行持久化

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 点赞和取消点赞
     *
     * @param shareCommentReply
     */
    public Boolean like(ShareCommentReply shareCommentReply) {
        Long commentId = shareCommentReply.getId();
        Integer replyType = shareCommentReply.getReplyType();
        LikeTypeEnum getType = LikeTypeEnum.getByCode(replyType);
        String detailKey = null;
        String countKey = null;
        if (getType == LikeTypeEnum.COMMENT) {
            detailKey = LIKE_COMMENT_STATUS_KEY + ":" + commentId + ":" + LoginContextHolder.getLoginId();
            countKey = LIKE_COMMENT_COUNT_KEY + ":" + commentId;
        } else if (getType == LikeTypeEnum.REPLY) {
            detailKey = LIKE_REPLY_STATUS_KEY + ":" + commentId + ":" + LoginContextHolder.getLoginId();
            countKey = LIKE_REPLY_COUNT_KEY + ":" + commentId;
        }
        Integer count = redisUtil.getInt(countKey); //点赞数量
        Integer changeCount = redisUtil.getInt(LIKE_CHANGE_COUNT_KEY); //变化数量


        if (redisUtil.exist(detailKey)) {
            //存在点赞
            if (Objects.isNull(count) || count <= 0) {
                return false;
            }
            if (Objects.isNull(changeCount) || changeCount <= 0) {
                return false;
            }
            redisUtil.increment(countKey, -1);
            redisUtil.del(detailKey);

            redisUtil.increment(LIKE_CHANGE_COUNT_KEY, -1);
        } else {
            redisUtil.increment(countKey, 1);
            redisUtil.set(detailKey, "1");

            redisUtil.increment(LIKE_CHANGE_COUNT_KEY, 1);
        }

        //获取最新的值
        count = redisUtil.getInt(countKey); //点赞数量
        changeCount = redisUtil.getInt(LIKE_CHANGE_COUNT_KEY); //变化数量

        if (changeCount >= Like_THRESHOLD) {
            ShareLikedMessage message = new ShareLikedMessage();
            message.setCommentId(commentId);
            message.setAccount(count.longValue());
            message.setType(replyType);
            //发送mq
            Message msg = MessageBuilder
                    .withBody(JSON.toJSONString(message).getBytes())
                    .build();
            rabbitTemplate.send("like_queue", msg);
            redisUtil.increment(LIKE_CHANGE_COUNT_KEY, -Like_THRESHOLD);
        }
        return true;
    }

    public Boolean isLiked(Long id) {
        String detailKey = LIKE_COMMENT_STATUS_KEY + ":" + id + ":" + LoginContextHolder.getLoginId();
        return redisUtil.exist(detailKey);
    }

    public Integer getAccount(ShareCommentReply shareCommentReply) {
        String countKey = LIKE_COMMENT_COUNT_KEY + ":" + shareCommentReply.getId();
        Integer count = redisUtil.getInt(countKey); //点赞数量
        return count;
    }
}
