package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 评论表(ShareMoment)实体类
 */
@Data
public class ShareMoment implements Serializable {
    private static final long serialVersionUID = 857775734358532570L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 圈子id
     */
    private Long circleId;
    /**
     * 交流内容
     */
    private String content;
    /**
     * 图片
     */
    private String picUrls;
    /**
     * 回复数
     */
    private Long replyCount;
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
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDeleted;
}

