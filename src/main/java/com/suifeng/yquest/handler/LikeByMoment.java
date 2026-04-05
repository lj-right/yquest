package com.suifeng.yquest.handler;

import com.alibaba.fastjson.JSON;
import com.suifeng.yquest.api.enums.LikeTypeEnum;
import com.suifeng.yquest.config.context.LoginContextHolder;
import com.suifeng.yquest.config.redis.RedisUtil;
import com.suifeng.yquest.entity.ShareLikedMessage;
import com.suifeng.yquest.entity.ShareMoment;
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
public class LikeByMoment {
    private static final String LIKE_MOMENT_STATUS_KEY = "like:moment:status";
    private static final String LIKE_MOMENT_COUNT_KEY = "like:moment:count";
    private static final String LIKE_CHANGE_COUNT_KEY = "like:change:count";
    private static Integer Like_THRESHOLD = 3;  //点赞数量达到一定值进行持久化

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 点赞和取消点赞
     *
     * @param shareMoment
     */
    public Boolean like(ShareMoment shareMoment) {
        Long momentId = shareMoment.getId();

        String detailKey = LIKE_MOMENT_STATUS_KEY + ":" + momentId + ":" + LoginContextHolder.getLoginId();
        String countKey = LIKE_MOMENT_COUNT_KEY + ":" + momentId;

        Integer count = redisUtil.getInt(countKey); //点赞数量
        Integer changeCount = redisUtil.getInt(LIKE_CHANGE_COUNT_KEY); //变化数量


        if (redisUtil.exist(detailKey)) {
            //存在点赞
            if (count <= 0) {
                return false;
            }
            if (changeCount < 0) {
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
            message.setMomentId(momentId);
            message.setAccount(count.longValue());
            message.setType(this.getLikeType());

            //发送mq
            Message msg = MessageBuilder
                    .withBody(JSON.toJSONString(message).getBytes())
                    .build();

            rabbitTemplate.send("like_queue", msg);
            Like_THRESHOLD += Like_THRESHOLD;
        }
        return true;
    }

    public Integer getLikeType() {

        return LikeTypeEnum.MOMENT.getCode();
    }

    public Boolean isLiked(Long id) {
        String detailKey = LIKE_MOMENT_STATUS_KEY + ":" + id + ":" + LoginContextHolder.getLoginId();
        return redisUtil.exist(detailKey);
    }

    public Integer getAccount(Long id) {
        String countKey = LIKE_MOMENT_COUNT_KEY + ":" + id;
        Integer count = redisUtil.getInt(countKey); //点赞数量
        return count;
    }
}
