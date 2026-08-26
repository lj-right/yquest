package com.suifeng.yquest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 内推申请信息（状态机主载体）
 */
@Getter
@Setter
@TableName("refer_apply")
public class ReferApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申请ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内推码ID
     */
    private Long referId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 简历ID
     */
    private Long resumeId;

    /**
     * 求职者用户ID
     */
    private Long jobSeekerId;

    /**
     * 求职者姓名
     */
    private String jobSeekerName;

    /**
     * 求职者邮箱
     */
    private String jobSeekerEmail;

    /**
     * 内推人用户ID
     */
    private Long referrerId;

    /**
     * 当前状态（0：已提交，3：简历筛选，4：初试，5：复试，6：终试，7：已发Offer，8：已接受Offer，10：已入职，11：试用期通过，12：试用期未通过；终态：2：被拒绝，9：已拒绝Offer，13：已撤回）
     */
    private Integer currentStatus;

    /**
     * 拒绝/未通过原因
     */
    private String rejectReason;

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
