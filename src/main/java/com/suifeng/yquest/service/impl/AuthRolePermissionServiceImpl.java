package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.entity.AuthRolePermission;
import com.suifeng.yquest.dao.AuthRolePermissionDao;
import com.suifeng.yquest.service.AuthRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色权限关联表(AuthRolePermission)表服务实现类
 */
@Service("authRolePermissionService")
public class AuthRolePermissionServiceImpl implements AuthRolePermissionService {
    @Resource
    private AuthRolePermissionDao authRolePermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthRolePermission queryById(Integer id) {
        return this.authRolePermissionDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param authRolePermission 实例对象
     * @return 实例对象
     */
    @Override
    public AuthRolePermission insert(AuthRolePermission authRolePermission) {
        this.authRolePermissionDao.insert(authRolePermission);
        return authRolePermission;
    }

}
