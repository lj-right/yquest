package com.suifeng.yquest.entity;

import com.suifeng.yquest.api.enums.LikeTypeEnum;
import lombok.Data;
import org.springframework.amqp.core.Message;

import java.io.Serializable;

@Data
public class ShareLikedMessage implements Serializable {
    /**
     * 动态评论id
     */
    private Long momentId;

    /**
    * 评论或回复id
     */
    private Long commentId;

    /**
    * 点赞类型
     */
    private Integer type;

    /**
     * 点赞总数
     */
    private Long account;
}
