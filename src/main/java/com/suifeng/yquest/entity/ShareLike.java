package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (ShareLike)实体类
 */
@Data
public class ShareLike implements Serializable {
    private static final long serialVersionUID = 534299084447506897L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 动态消息id
     */
    private Long momentId;
    /**
     * 评论id
     */
    private Long commentId;
    /**
     * 回复类型
     */
    private Integer type;

    /**
     * 点赞总数
     */
    private Long account;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 是否删除
     */
    private Integer isDeleted;
}

