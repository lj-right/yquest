package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 角色表(AuthRole)实体类
 */
@Data
public class AuthRole implements Serializable {
    private static final long serialVersionUID = -33301397771157650L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色唯一标识
     */
    private String roleKey;
    /**
     * 状态 0启用 1禁用
     */
    private Integer status;
    /**
     * 1 符合部门权限的所有数据 2本部门 3部门级以下
     */
    private Integer dataRange;
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

