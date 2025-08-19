package com.suifeng.yquest.service.impl;

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
    public AuthUserRole queryById(Integer id) {
        return this.authUserRoleDao.queryById(id);
    }



    /**
     * 新增数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public AuthUserRole insert(AuthUserRole authUserRole) {
        this.authUserRoleDao.insert(authUserRole);
        return authUserRole;
    }

}
