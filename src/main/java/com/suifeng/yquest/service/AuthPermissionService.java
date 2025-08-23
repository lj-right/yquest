package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.AuthPermission;

import java.util.List;

/**
 * 权限表(AuthPermission)表服务接口
 */
public interface AuthPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthPermission queryById(Long id);

    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    Boolean insert(AuthPermission authPermission);

    List<String> getPermission(AuthPermission authPermission);

    List<AuthPermission> queryByRoleList(List<Long> permissionIdList);
}
