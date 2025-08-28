package com.suifeng.yquest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.suifeng.yquest.api.common.TreeNode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * 评论及回复信息
 */
@Getter
@Setter
@TableName("share_comment_reply")
public class ShareCommentReply extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 原始动态ID
     */
    private Long momentId;

    /**
     * 回复类型 1评论 2回复
     */
    private Integer replyType;

    /**
     * 评论目标id
     */
    private Long toId;

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
     * 回复人是否作者 1=是 0=否
     */
    private Integer replyAuthor;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片内容
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
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;

    @TableField("parent_id")
    private Long parentId;

    @Override
    public Long getNodeId() {
        return id;
    }

    @Override
    public Long getNodePId() {
        return parentId;
    }

    // 添加 children 字段（非数据库字段）
    @TableField(exist = false)
    private List<TreeNode> children;

    @TableField(exist = false)
    private Boolean rootNode;

    @TableField(exist = false)
    private Boolean leafNode;


    @Override
    public void setChildren(List<TreeNode> children) {
        this.children = children;
        this.rootNode = Objects.equals(getNodePId(), -1L);
        this.leafNode = children == null || children.isEmpty();
    }


}
