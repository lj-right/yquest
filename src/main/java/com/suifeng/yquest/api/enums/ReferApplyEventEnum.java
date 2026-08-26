package com.suifeng.yquest.api.enums;

import com.suifeng.yquest.constants.ReferConstant;
import lombok.Getter;

import java.util.Arrays;

/**
 * 内推申请事件枚举（状态机转移表：事件 + 合法来源状态 -> 目标状态 + 允许角色）
 */
@Getter
public enum ReferApplyEventEnum {
    /**
     * 求职者撤回申请（各阶段均可撤回，统一流转至"已撤回"终态）
     */
    WITHDRAW(new ReferApplyStatusEnum[]{
            ReferApplyStatusEnum.SUBMITTED,
            ReferApplyStatusEnum.RESUME_SCREENING,
            ReferApplyStatusEnum.FIRST_INTERVIEW,
            ReferApplyStatusEnum.SECOND_INTERVIEW,
            ReferApplyStatusEnum.FINAL_INTERVIEW,
            ReferApplyStatusEnum.OFFERED}, ReferApplyStatusEnum.WITHDRAWN, ReferConstant.ROLES_SEEKER, false),

    /**
     * 内推人受理申请，进入简历筛选
     */
    ACCEPT(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.SUBMITTED},
            ReferApplyStatusEnum.RESUME_SCREENING, ReferConstant.ROLES_REFERRER, false),

    /**
     * 内推人直接拒绝申请
     */
    REJECT(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.SUBMITTED},
            ReferApplyStatusEnum.REJECTED, ReferConstant.ROLES_REFERRER, true),

    /**
     * 简历筛选通过，进入初试
     */
    SCREEN_PASS(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.RESUME_SCREENING},
            ReferApplyStatusEnum.FIRST_INTERVIEW, ReferConstant.ROLES_REFERRER, false),

    /**
     * 简历筛选不通过
     */
    SCREEN_REJECT(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.RESUME_SCREENING},
            ReferApplyStatusEnum.REJECTED, ReferConstant.ROLES_REFERRER, true),

    /**
     * 面试通过（初试->复试 / 复试->终试 / 终试->已发Offer，目标状态由来源状态推导）
     */
    INTERVIEW_PASS(new ReferApplyStatusEnum[]{
            ReferApplyStatusEnum.FIRST_INTERVIEW,
            ReferApplyStatusEnum.SECOND_INTERVIEW,
            ReferApplyStatusEnum.FINAL_INTERVIEW}, null, ReferConstant.ROLES_REFERRER, false),

    /**
     * 面试未通过
     */
    INTERVIEW_FAIL(new ReferApplyStatusEnum[]{
            ReferApplyStatusEnum.FIRST_INTERVIEW,
            ReferApplyStatusEnum.SECOND_INTERVIEW,
            ReferApplyStatusEnum.FINAL_INTERVIEW},
            ReferApplyStatusEnum.REJECTED, ReferConstant.ROLES_REFERRER, true),

    /**
     * 求职者接受Offer
     */
    ACCEPT_OFFER(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.OFFERED},
            ReferApplyStatusEnum.OFFER_ACCEPTED, ReferConstant.ROLES_SEEKER, false),

    /**
     * 求职者拒绝Offer
     */
    DECLINE_OFFER(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.OFFERED},
            ReferApplyStatusEnum.OFFER_DECLINED, ReferConstant.ROLES_SEEKER, false),

    /**
     * 确认入职
     */
    CONFIRM_ONBOARD(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.OFFER_ACCEPTED},
            ReferApplyStatusEnum.ONBOARDED, ReferConstant.ROLES_REFERRER, false),

    /**
     * 试用期通过（内推成功）
     */
    PROBATION_PASS(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.ONBOARDED},
            ReferApplyStatusEnum.PROBATION_PASSED, ReferConstant.ROLES_REFERRER, false),

    /**
     * 试用期未通过
     */
    PROBATION_FAIL(new ReferApplyStatusEnum[]{ReferApplyStatusEnum.ONBOARDED},
            ReferApplyStatusEnum.PROBATION_FAILED, ReferConstant.ROLES_REFERRER, true);

    /**
     * 合法的来源状态集合
     */
    public final ReferApplyStatusEnum[] fromStatuses;

    /**
     * 目标状态（INTERVIEW_PASS 为动态目标，取值为 null，见 getToStatus）
     */
    public final ReferApplyStatusEnum toStatus;

    /**
     * 允许触发该事件的角色（auth_role.role_key）
     */
    public final String[] allowedRoles;

    /**
     * 是否必须填写原因（拒绝类事件）
     */
    public final boolean needReason;

    ReferApplyEventEnum(ReferApplyStatusEnum[] fromStatuses, ReferApplyStatusEnum toStatus,
                        String[] allowedRoles, boolean needReason) {
        this.fromStatuses = fromStatuses;
        this.toStatus = toStatus;
        this.allowedRoles = allowedRoles;
        this.needReason = needReason;
    }

    /**
     * 判断当前状态是否允许触发该事件
     */
    public boolean canFrom(ReferApplyStatusEnum fromStatus) {
        if (fromStatus == null || fromStatus.isFinalState()) {
            return false;
        }
        return Arrays.asList(fromStatuses).contains(fromStatus);
    }

    /**
     * 判断角色是否允许触发该事件
     */
    public boolean allowRole(String roleKey) {
        return Arrays.asList(allowedRoles).contains(roleKey);
    }

    /**
     * 获取目标状态
     */
    public ReferApplyStatusEnum getToStatus(ReferApplyStatusEnum fromStatus) {
        if (this != INTERVIEW_PASS) {
            return toStatus;
        }
        switch (fromStatus) {
            case FIRST_INTERVIEW:
                return ReferApplyStatusEnum.SECOND_INTERVIEW;
            case SECOND_INTERVIEW:
                return ReferApplyStatusEnum.FINAL_INTERVIEW;
            case FINAL_INTERVIEW:
                return ReferApplyStatusEnum.OFFERED;
            default:
                return null;
        }
    }

    public static ReferApplyEventEnum getByName(String eventName) {
        for (ReferApplyEventEnum eventEnum : ReferApplyEventEnum.values()) {
            if (eventEnum.name().equalsIgnoreCase(eventName)) {
                return eventEnum;
            }
        }
        return null;
    }
}
