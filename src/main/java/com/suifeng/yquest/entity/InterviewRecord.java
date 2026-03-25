package com.suifeng.yquest.entity;

import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (InterviewRecord)实体类
 */
@Data
public class InterviewRecord extends PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 流程ID
     */
    private Long processId;

    /**
     * 简历ID
     */
    private Long resumeId;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 面试阶段（1：初试，2：复试，3：终试）
     */
    private Integer stage;

    /**
     * 面试时间
     */
    private Date interviewTime;

    /**
     * 面试官ID
     */
    private Long interviewerId;

    /**
     * 面试官姓名
     */
    private String interviewerName;

    /**
     * 面试类型（现场/视频/电话）
     */
    private String interviewType;

    /**
     * 面试地点
     */
    private String location;

    /**
     * 面试结果（0：待评估，1：通过，2：未通过）
     */
    private Integer result;

    /**
     * 面试评语
     */
    private String comments;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 是否删除（0：否，1：是）
     */
    private Integer isDeleted;

    // 构造方法、getter和setter由lombok自动生成
}
