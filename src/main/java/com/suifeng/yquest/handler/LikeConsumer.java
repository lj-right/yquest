package com.suifeng.yquest.handler;

import com.suifeng.yquest.api.enums.LikeTypeEnum;
import com.suifeng.yquest.entity.ShareLike;
import com.suifeng.yquest.entity.ShareLikedMessage;
import com.suifeng.yquest.service.ShareLikeService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeConsumer {

    @Resource
    private ShareLikeService shareLikeService;

    @RabbitListener(queuesToDeclare = @Queue("like_queue"))
    public void recevie(ShareLikedMessage message) {
        System.out.println("-------收到点赞-------" + message);
        ShareLike shareLike = new ShareLike();
        switch (LikeTypeEnum.getByCode(message.getType())) {
            case MOMENT:
                shareLike.setMomentId(message.getMomentId());
            case COMMENT:
                shareLike.setCommentId(message.getCommentId());
            case REPLY:
                shareLike.setCommentId(message.getCommentId());
        }
        shareLike.setType(message.getType());
        shareLike.setAccount(message.getAccount());
        Boolean update = shareLikeService.update(shareLike);
        if (!update) {
            shareLikeService.add(shareLike);
        }
    }
}
