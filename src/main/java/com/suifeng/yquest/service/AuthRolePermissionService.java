package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.AuthRolePermission;

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
    AuthRolePermission queryById(Integer id);


    /**
     * 新增数据
     *
     * @param authRolePermission 实例对象
     * @return 实例对象
     */
    AuthRolePermission insert(AuthRolePermission authRolePermission);


}
