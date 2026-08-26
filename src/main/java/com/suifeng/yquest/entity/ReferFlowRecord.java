package com.suifeng.yquest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 内推流转记录信息（状态机审计，只插入不更新）
 */
@Getter
@Setter
@TableName("refer_flow_record")
public class ReferFlowRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内推申请ID
     */
    private Long referApplyId;

    /**
     * 流转前状态
     */
    private Integer fromStatus;

    /**
     * 流转后状态
     */
    private Integer toStatus;

    /**
     * 触发事件（accept/reject/screen_pass/interview_pass/...）
     */
    private String event;

    /**
     * 操作人用户ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作时角色
     */
    private String operatorRole;

    /**
     * 备注（拒绝原因/面试评语摘要等）
     */
    private String remark;

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
     * 是否删除（0：否，1：是）
     */
    private Integer isDeleted;
}
