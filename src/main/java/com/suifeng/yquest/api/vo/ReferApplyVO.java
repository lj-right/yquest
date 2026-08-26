package com.suifeng.yquest.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 内推申请信息
 */
@Getter
@Setter
public class ReferApplyVO implements Serializable {

    /**
     * 申请ID
     */
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
     * 公司名称
     */
    private String companyName;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 职位名称
     */
    private String jobTitle;

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
     * 当前状态码
     */
    private Integer currentStatus;

    /**
     * 当前状态描述
     */
    private String currentStatusDesc;

    /**
     * 拒绝/未通过原因
     */
    private String rejectReason;

    /**
     * 创建时间（毫秒时间戳）
     */
    private Long createdTime;
}
