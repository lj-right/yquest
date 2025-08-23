package com.suifeng.yquest.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Long id;
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
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    /**
     * 是否删除
     */
    private Integer isDeleted;
}

