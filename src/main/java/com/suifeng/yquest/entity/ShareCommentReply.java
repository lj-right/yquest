package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 评论回复表(ShareCommentReply)实体类
 */
@Data
public class ShareCommentReply implements Serializable {
    private static final long serialVersionUID = 264103043183241699L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 评论id
     */
    private Long momentId;
    /**
     * 评论1 回复2
     */
    private Integer replyType;
    /**
     * 评论id
     */
    private String toId;
    /**
     * 父类id
     */
    private Long parentId;
    /**
     * 评论人
     */
    private String toUser;
    /**
     * 评论人是否作者 1=是 0=否
     */
    private Integer toUserAuthor;
    /**
     * 回复目标id
     */
    private Long replyId;
    /**
     * 回复人
     */
    private String replyUser;
    /**
     * 回复人是否 作者 1=是 0=否
     */
    private Integer replyAuthor;
    /**
     * 评论回复内容
     */
    private String content;
    /**
     * 图片
     */
    private String picUrls;
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

