package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户信息表(AuthUser)实体类
 */
@Data
public class AuthUser implements Serializable {
    private static final long serialVersionUID = -48428390573994316L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 登录方式 1 name 2 email
     */
    private Integer status;
    /**
     * 个人介绍
     */
    private String introduce;
    /**
     * 验证码
     */
    private String extJson;
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
     * 是否删除
     */
    private Integer isDeleted;
}

