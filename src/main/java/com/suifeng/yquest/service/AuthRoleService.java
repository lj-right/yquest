package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.AuthRole;

/**
 * 角色表(AuthRole)表服务接口
 */
public interface AuthRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRole queryById(Integer id);


    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    AuthRole insert(AuthRole authRole);


}
