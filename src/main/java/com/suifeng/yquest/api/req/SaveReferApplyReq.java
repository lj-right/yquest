package com.suifeng.yquest.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 求职者提交内推申请入参
 */
@Getter
@Setter
public class SaveReferApplyReq implements Serializable {

    /**
     * 内推码ID
     */
    private Long referId;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 简历ID
     */
    private Long resumeId;
}
