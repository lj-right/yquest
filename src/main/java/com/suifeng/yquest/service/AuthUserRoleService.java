package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.AuthUserRole;

/**
 * 用户角色表(AuthUserRole)表服务接口
 */
public interface AuthUserRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthUserRole queryById(Integer id);


    /**
     * 新增数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    AuthUserRole insert(AuthUserRole authUserRole);

}
