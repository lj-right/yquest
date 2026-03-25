package com.suifeng.yquest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (InterviewProcess)实体类
 */
@Data
public class InterviewProcess extends PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 流程ID
     */
    private Long id;

    /**
     * 简历ID
     */
    private Long resumeId;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 当前阶段（0：简历筛选，1：初试，2：复试，3：终试，4：offer，5：已入职，6：已拒绝）
     */
    private Integer currentStage;

    /**
     * 下一阶段时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date nextStageTime;

    /**
     * 面试官ID
     */
    private Long interviewerId;

    /**
     * 面试官姓名
     */
    private String interviewerName;

    /**
     * 状态（0：进行中，1：已完成，2：已取消）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updatedTime;

    /**
     * 是否删除（0：否，1：是）
     */
    private Integer isDeleted;

    // 构造方法、getter和setter由lombok自动生成
}
