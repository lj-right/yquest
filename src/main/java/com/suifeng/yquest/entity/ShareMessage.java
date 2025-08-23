package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 信息提醒表(ShareMessage)实体类
 */
@Data
public class ShareMessage implements Serializable {
    private static final long serialVersionUID = 445517594160472063L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 回复人id
     */
    private String fromId;
    /**
     * 被回复人id
     */
    private String toId;
    /**
     * 回复内容
     */
    private String content;
    /**
     * 是否已读
     */
    private Integer isRead;
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

