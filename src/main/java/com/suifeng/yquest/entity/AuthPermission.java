package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限表(AuthPermission)实体类
 */
@Data
public class AuthPermission implements Serializable {
    private static final long serialVersionUID = 533943435653998425L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 父id
     */
    private Integer parentId;
    /**
     * 权限类型 0菜单 1操作
     */
    private Integer type;
    /**
     * 菜单路由
     */
    private String menuUrl;
    /**
     * 状态 0启用 1禁用
     */
    private Integer status;
    /**
     * 展示状态 0展示 1隐藏
     */
    private Integer show;
    /**
     * 图标
     */
    private String icon;
    /**
     * 权限唯一标识
     */
    private String permissionKey;
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

