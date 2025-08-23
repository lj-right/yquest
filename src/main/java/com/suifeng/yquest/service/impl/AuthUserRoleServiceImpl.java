package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.AuthUserRole;
import com.suifeng.yquest.dao.AuthUserRoleDao;
import com.suifeng.yquest.service.AuthUserRoleService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户角色表(AuthUserRole)表服务实现类
 */
@Service("authUserRoleService")
public class AuthUserRoleServiceImpl implements AuthUserRoleService {
    @Resource
    private AuthUserRoleDao authUserRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthUserRole queryById(Long id) {
        return this.authUserRoleDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(AuthUserRole authUserRole) {
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.authUserRoleDao.insert(authUserRole) > 0;
    }

    @Override
    public Boolean deleteById(Long id) {
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setId(id);
        authUserRole.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        return this.authUserRoleDao.update(authUserRole) > 0;
    }

    @Override
    public AuthUserRole queryByUserId(Long userId) {
        return this.authUserRoleDao.queryByUserId(userId);
    }

    @Override
    public Boolean update(AuthUserRole userRole) {
        return this.authUserRoleDao.update(userRole)>0;
    }
}
