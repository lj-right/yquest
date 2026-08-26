package com.suifeng.yquest.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 内推申请状态流转入参
 */
@Getter
@Setter
public class AdvanceReferApplyReq implements Serializable {

    /**
     * 内推申请ID
     */
    private Long applyId;

    /**
     * 触发事件（WITHDRAW/ACCEPT/REJECT/SCREEN_PASS/SCREEN_REJECT/INTERVIEW_PASS/INTERVIEW_FAIL/ACCEPT_OFFER/DECLINE_OFFER/CONFIRM_ONBOARD/PROBATION_PASS/PROBATION_FAIL）
     */
    private String event;

    /**
     * 备注（拒绝类事件必填）
     */
    private String remark;
}
