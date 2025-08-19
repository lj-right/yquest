package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.AuthPermission;

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
    AuthPermission queryById(Integer id);

    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    AuthPermission insert(AuthPermission authPermission);

}
