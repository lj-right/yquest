package com.suifeng.yquest.api.enums;

import lombok.Getter;

/**
 * 内推申请状态枚举（状态机节点）
 */
@Getter
public enum ReferApplyStatusEnum {
    SUBMITTED(0, "已提交待确认", false),
    REJECTED(2, "被拒绝", true),
    RESUME_SCREENING(3, "简历筛选", false),
    FIRST_INTERVIEW(4, "初试", false),
    SECOND_INTERVIEW(5, "复试", false),
    FINAL_INTERVIEW(6, "终试", false),
    OFFERED(7, "已发Offer", false),
    OFFER_ACCEPTED(8, "已接受Offer", false),
    OFFER_DECLINED(9, "已拒绝Offer", true),
    ONBOARDED(10, "已入职", false),
    PROBATION_PASSED(11, "试用期通过", true),
    PROBATION_FAILED(12, "试用期未通过", true),
    WITHDRAWN(13, "已撤回", true);

    public int code;

    public String desc;

    /**
     * 是否终态（终态不允许再流转）
     */
    public boolean finalState;

    ReferApplyStatusEnum(int code, String desc, boolean finalState) {
        this.code = code;
        this.desc = desc;
        this.finalState = finalState;
    }

    public static ReferApplyStatusEnum getByCode(int codeVal) {
        for (ReferApplyStatusEnum statusEnum : ReferApplyStatusEnum.values()) {
            if (statusEnum.code == codeVal) {
                return statusEnum;
            }
        }
        return null;
    }
}
