package com.suifeng.yquest.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.util.SaResult;
import com.suifeng.yquest.entity.AuthUser;
import com.suifeng.yquest.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表(AuthUser)表服务接口
 */
public interface AuthUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthUser queryById(Long id);


    /**
     * 新增数据
     *
     * @param authUser 实例对象
     * @return 实例对象
     */
    Boolean insert(AuthUser authUser);

    /**
     * 修改数据
     *
     * @param authUser 实例对象
     * @return 实例对象
     */
    Boolean update(AuthUser authUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Boolean deleteById(Long id);

    AuthUser queryByEmail(AuthUser authUser);

    Boolean sendEmail(AuthUser user);

    String getEmailcaptcha(AuthUser user);

    AuthUser queryByName(AuthUser authUser);

    SaTokenInfo doLogin(AuthUser user);

    UserInfo getUserInfo(AuthUser userName);

    List<AuthUser> listUserInfoByIds(List<String> usernamesList);

    Map<String, UserInfo> batchGetUserInfo(List<String> userNameList);
}
