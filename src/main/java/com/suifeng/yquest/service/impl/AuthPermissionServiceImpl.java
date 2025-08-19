package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.entity.AuthPermission;
import com.suifeng.yquest.dao.AuthPermissionDao;
import com.suifeng.yquest.service.AuthPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 权限表(AuthPermission)表服务实现类
 */
@Service("authPermissionService")
public class AuthPermissionServiceImpl implements AuthPermissionService {
    @Resource
    private AuthPermissionDao authPermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthPermission queryById(Integer id) {
        return this.authPermissionDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    @Override
    public AuthPermission insert(AuthPermission authPermission) {
        this.authPermissionDao.insert(authPermission);
        return authPermission;
    }
}
