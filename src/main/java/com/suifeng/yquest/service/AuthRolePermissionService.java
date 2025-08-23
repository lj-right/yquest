package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.AuthRolePermission;

import java.util.List;

/**
 * 角色权限关联表(AuthRolePermission)表服务接口
 */
public interface AuthRolePermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRolePermission queryById(Long id);


    /**
     * 新增数据
     *
     * @param authRolePermission 实例对象
     * @return 实例对象
     */
    Boolean insert(AuthRolePermission authRolePermission);


    List<AuthRolePermission> queryByCondition(AuthRolePermission authRolePermission);
}
