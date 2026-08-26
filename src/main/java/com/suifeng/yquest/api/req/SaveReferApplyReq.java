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
     * 内推码ID（可选：通过内推码投递时必传；官方职位大厅投递无需传，内推人默认为职位发布人）
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
