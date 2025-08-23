package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.AuthRole;
import com.suifeng.yquest.dao.AuthRoleDao;
import com.suifeng.yquest.service.AuthRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色表(AuthRole)表服务实现类
 */
@Service("authRoleService")
public class AuthRoleServiceImpl implements AuthRoleService {
    @Resource
    private AuthRoleDao authRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthRole queryById(Long id) {
        return this.authRoleDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(AuthRole authRole) {
        authRole.setStatus(0);
        authRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.authRoleDao.insert(authRole) > 0;
    }

    @Override
    public AuthRole queryByCondition(AuthRole authRole) {
        return this.authRoleDao.queryByCondition(authRole);
    }
}
