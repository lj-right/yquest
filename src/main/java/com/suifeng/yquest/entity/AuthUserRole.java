package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户角色表(AuthUserRole)实体类
 */
@Data
public class AuthUserRole implements Serializable {
    private static final long serialVersionUID = -81592708099376347L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Integer roleId;
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

