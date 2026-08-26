package com.suifeng.yquest.constants;

/**
 * 内推流程相关常量
 */
public class ReferConstant {

    /**
     * 求职者角色（auth_role.role_key）
     */
    public static final String ROLE_JOB_SEEKER = "jobseek_user";

    /**
     * 内推人/招聘者角色
     */
    public static final String ROLE_REFERRER = "recruiter_user";

    /**
     * 管理员角色
     */
    public static final String ROLE_ADMIN = "manage_user";

    /**
     * 求职者操作事件允许的角色
     */
    public static final String[] ROLES_SEEKER = new String[]{ROLE_JOB_SEEKER};

    /**
     * 内推人操作事件允许的角色
     */
    public static final String[] ROLES_REFERRER = new String[]{ROLE_REFERRER, ROLE_ADMIN};

    /**
     * 求职者与内推人均可操作的事件（如撤回由求职者发起）
     */
    public static final String[] ROLES_ALL = new String[]{ROLE_JOB_SEEKER, ROLE_REFERRER, ROLE_ADMIN};
}
