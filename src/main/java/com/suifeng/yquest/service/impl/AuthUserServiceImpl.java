package com.suifeng.yquest.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.zvo.email.Email;
import com.google.gson.Gson;
import com.suifeng.yquest.api.enums.AuthUserStatusEnum;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.api.enums.LoginInfoTypeEnum;
import com.suifeng.yquest.constants.AuthConstant;
import com.suifeng.yquest.entity.*;
import com.suifeng.yquest.dao.AuthUserDao;
import com.suifeng.yquest.handler.LoginTypeHandler;
import com.suifeng.yquest.handler.LoginTypeHandlerFactory;
import com.suifeng.yquest.redis.RedisUtil;
import com.suifeng.yquest.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户信息表(AuthUser)表服务实现类
 */
@Service("authUserService")
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {
    private static final String LOGIN_PREFIX = "loginCode";

    private String authPermissionPrefix = "auth.permission";

    private static final String authRolePrefix = "auth.role";

    private static final String salt = "suifeng";
    @Resource
    private AuthUserDao userDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private LoginTypeHandlerFactory loginTypeHandlerFactory;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthUser queryById(Long id) {
        return this.userDao.queryById(id);
    }


    /**
     * 新增用户数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insert(AuthUser user) {
        AuthUser name = new AuthUser();
        name.setUserName(SaSecureUtil.md5BySalt(user.getEmail(), salt));
        //校验用户是否存在
        AuthUser user1 = userDao.queryByName(name);
        if (Objects.nonNull(user1)) {
            return false;
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(SaSecureUtil.md5BySalt(user.getPassword(), salt));
        }
        if (StringUtils.isBlank(user.getNickName())) {
            user.setNickName("fans");
        }
        user.setUserName(SaSecureUtil.md5BySalt(user.getEmail(), salt));
        user.setStatus(AuthUserStatusEnum.OPEN.getCode());
        user.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        int count = userDao.insert(user);

        //关联角色
        AuthRole authRole = new AuthRole();
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        authRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        AuthRole roleResult = authRoleService.queryByCondition(authRole);
        Long roleId = roleResult.getId();
        Long userId = user.getId();

        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(userId);
        authUserRole.setRoleId(roleId);
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUserRoleService.insert(authUserRole);

        //缓存角色信息
        String roleKey = redisUtil.buildKey(authRolePrefix, user.getEmail());
        List<AuthRole> roleList = new LinkedList<>();
        roleList.add(authRole);
        redisUtil.set(roleKey, new Gson().toJson(roleList));

        AuthRolePermission authRolePermission = new AuthRolePermission();
        authRolePermission.setRoleId(roleId);
        List<AuthRolePermission> rolePermissionList = authRolePermissionService.
                queryByCondition(authRolePermission);

        List<Long> permissionIdList = rolePermissionList.stream()
                .map(AuthRolePermission::getPermissionId).collect(Collectors.toList());
        //根据roleId查权限
        List<AuthPermission> permissionList = authPermissionService.queryByRoleList(permissionIdList);
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, user.getEmail());
        redisUtil.set(permissionKey, new Gson().toJson(permissionList));

        return count > 0;
    }

    /**
     * 修改数据
     *
     * @param authUser 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean update(AuthUser authUser) {
        return this.userDao.update(authUser) > 0;
    }

    /**
     * 通过主键删除数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(Long userId) {
        AuthUserRole userRole = authUserRoleService.queryByUserId(userId);
        userRole.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Boolean result = authUserRoleService.update(userRole);
        if (!result) {
            return false;
        }

        AuthUser user = new AuthUser();
        user.setId(userId);
        user.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        return this.userDao.update(user) >0;
    }

    /**
     * 根据邮箱查询用户
     *
     * @param authUser
     * @return
     */
    @Override
    public AuthUser queryByEmail(AuthUser authUser) {
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return userDao.queryByEmail(authUser);
    }

    /**
     * 根据邮箱查询用户
     *
     * @param authUser
     * @return
     */
    @Override
    public AuthUser queryByName(AuthUser authUser) {
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return userDao.queryByName(authUser);
    }

    /**
     * 扫码登录
     *
     * @param Code
     * @return
     */
//    @Override
//    public SaTokenInfo doLogin(String Code) {
//    todo：实现扫码登录
//        String loginKey = redisUtil.buildKey(LOGIN_PREFIX, Code);
//        String openId = redisUtil.get(loginKey);
//        if (StringUtils.isBlank(openId)) {
//            return null;
//        }
//        AuthUser authUser = new AuthUser();
//        authUser.setUserName(openId);
//        this.insert(authUser);
//        StpUtil.login(openId); //以微信公众号openId作为唯一的标识
//        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
//        return tokenInfo;
//    }

    /**
     * 发送邮箱验证码
     *
     * @param user
     * @return
     */
    @Override
    public Boolean sendEmail(AuthUser user) {
        String host = "smtp.163.com";
        String username = "XXX@163.com"; //您的163邮箱
        String password = "XXX";
        Email mail = new Email(host, username, password); //创建
        String email = user.getEmail();
        Random random = new Random();
        int verificationCode = random.nextInt(900000) + 100000;
        String codeKey = redisUtil.buildKey(LOGIN_PREFIX, user.getEmail());
        redisUtil.setNx(codeKey, String.valueOf(verificationCode), 5L, TimeUnit.MINUTES);
        String CodeContent = "【yquest】您的注册验证码为：" + verificationCode + "该验证码5分钟内有效，请及时验证。";
        mail.sendHtmlMail(email, "【yquest】验证你的电子邮件地址", CodeContent);
        return true;
    }

    /**
     * 获取邮箱验证码
     *
     * @param user
     * @return
     */
    @Override
    public String getEmailcaptcha(AuthUser user) {
        String result = redisUtil.get(redisUtil.buildKey(LOGIN_PREFIX, String.valueOf(user.getEmail())));
        return result;
    }


    /**
     * 工厂+策略实现统一登录
     * @param user
     * @return
     */
    @Override
    public SaTokenInfo doLogin(AuthUser user) {
        LoginTypeHandler loginHandler = loginTypeHandlerFactory.getHandler(user.getStatus());
        if (loginHandler == null) {
            throw new UnsupportedOperationException("未找到对应的登录类型处理器: " + user.getStatus());
        }
        SaTokenInfo saTokenInfo = loginHandler.login(user);
        return saTokenInfo;
    }
}
