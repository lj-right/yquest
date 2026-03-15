package com.suifeng.yquest.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.gson.Gson;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.AuthUserStatusEnum;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.constants.AuthConstant;
import com.suifeng.yquest.entity.*;
import com.suifeng.yquest.dao.AuthUserDao;
import com.suifeng.yquest.handler.LoginTypeHandler;
import com.suifeng.yquest.handler.LoginTypeHandlerFactory;
import com.suifeng.yquest.config.redis.RedisUtil;
import com.suifeng.yquest.service.*;
import com.suifeng.yquest.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户信息表(AuthUser)表服务实现类
 */
@Service("authUserService")
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {
    private static final String LOGIN_PREFIX = "loginCode";

    private static final String REGISTER_PREFIX = "registerCode";

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
            user.setNickName(user.getNickName());
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
     * 根据邮箱查询用户别名 NickName
     *
     */
    @Override
    public String queryNickNameByEmail(String email) {
        return userDao.queryNickNameByEmail(email);
    }

    /**
     * 分页查询用户
     * @param authUser
     * @return
     */
    @Override
    public PageResult<AuthUser> queryPage(AuthUser authUser) {
        PageResult<AuthUser> pageResult = new PageResult<>();
        pageResult.setPageNo(authUser.getPageNo());
        pageResult.setPageSize(authUser.getPageSize());
        int start = (authUser.getPageNo() - 1) * authUser.getPageSize();

        int count = userDao.countByCondition(authUser);
        if(count == 0){
            return pageResult;
        }
        List<AuthUser> userList =  userDao.queryAllUsers(start,authUser.getPageSize());
        pageResult.setRecords(userList);
        pageResult.setTotal(count);
        return pageResult;
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
     * 注册 | 忘记密码：发送邮箱验证码
     *
     * @param user
     * @return
     */
    @Override
    public Boolean sendEmail(AuthUser user) {
        String email = user.getEmail();
        // 验证码频率限制
        String sendLimitKey = redisUtil.buildKey("send_limit", email);
        boolean canSend = redisUtil.setNx(sendLimitKey, "1", 2L, TimeUnit.MINUTES);
        if (!canSend) {
            return false; // 2分钟内不允许重复发送
        }

        Random random = new Random();
        int verificationCode = random.nextInt(900000) + 100000;
        String codeKey = redisUtil.buildKey(REGISTER_PREFIX, user.getEmail());
        redisUtil.setNx(codeKey, String.valueOf(verificationCode), 5L, TimeUnit.MINUTES);
        String CodeContent = "【yquest】您的注册验证码为：" + verificationCode + ",该验证码5分钟内有效，请及时验证。";

        Set EmailSet = new HashSet<>();
        EmailSet.add(email);
        EmailUtil.sendEmailUtil(EmailSet, "【yquest】验证您的电子邮件地址", CodeContent);
        return true;
    }

    /**
     * 注册 | 忘记密码：校验邮箱验证码
     *
     * @param user
     * @return
     */
    @Override
    public Boolean getEmailcaptcha(AuthUser user) {
        String result = redisUtil.get(redisUtil.buildKey(REGISTER_PREFIX, String.valueOf(user.getEmail())));
        return result.equals(user.getExtJson());
    }

    /**
     * 忘记密码
     * @param user
     * @return
     */
    @Override
    public Boolean forgetPwd(AuthUser user) {
        if(this.getEmailcaptcha(user)){
            //查找 用户的id。用于更新密码
            AuthUser authUser = this.queryByEmail(user);
            //重置为默认密码123456
            user.setPassword("123456");
            user.setId(authUser.getId());
            this.userDao.update(user);
        }
        return false;
    }

    /**
     * 登录：发送邮箱验证码
     *
     * @param user
     * @return
     */
    @Override
    public Boolean sendEmailtoLogin(AuthUser user) {
        String email = user.getEmail();
        // 验证码频率限制
        String sendLimitKey = redisUtil.buildKey("send_limit", email);
        boolean canSend = redisUtil.setNx(sendLimitKey, "1", 2L, TimeUnit.MINUTES);
        if (!canSend) {
            return false; // 2分钟内不允许重复发送
        }

        Random random = new Random();
        int verificationCode = random.nextInt(900000) + 100000;
        String codeKey = redisUtil.buildKey(LOGIN_PREFIX, user.getEmail());
        redisUtil.setNx(codeKey, String.valueOf(verificationCode), 5L, TimeUnit.MINUTES);
        String CodeContent = "【yquest】您的登录验证码为：" + verificationCode + ",该验证码5分钟内有效，请及时验证。";

        Set EmailSet = new HashSet<>();
        EmailSet.add(email);
        EmailUtil.sendEmailUtil(EmailSet, "【yquest】验证您的电子邮件地址", CodeContent);
        return true;
    }



    /**
     * 登录：校验邮箱验证码
     *
     * @param user
     * @return
     */
    @Override
    public Boolean getLoginEmailCaptcha(AuthUser user) {
        String result = redisUtil.get(redisUtil.buildKey(LOGIN_PREFIX, String.valueOf(user.getEmail())));
        return result.equals(user.getExtJson());
    }

    /**
     * 校验用户是否登录
     *
     * @return
     */
    @Override
    public Boolean isLogin() {
        return (Boolean)StpUtil.isLogin();
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


    @Override
    public UserInfo getUserInfo(AuthUser user) {
        AuthUser authUser = new AuthUser();
        authUser.setUserName(user.getUserName());
        List<AuthUser> result = userDao.queryAllByLimit(authUser);
        if (CollectionUtils.isEmpty(result)) {
            return new UserInfo();
        }
        UserInfo userInfo = new UserInfo();
        AuthUser data = result.get(0);
        userInfo.setUserName(data.getUserName());
        userInfo.setNickName(data.getNickName());
        userInfo.setAvatar(data.getAvatar());
        return userInfo;
    }


    @Override
    public List<AuthUser> listUserInfoByIds(List<String> usernamesList) {
        List<AuthUser> result = userDao.listUserInfoByIds(usernamesList);
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result;
    }

    @Override
    public Map<String, UserInfo> batchGetUserInfo(List<String> userNameList) {
        if (CollectionUtils.isEmpty(userNameList)) {
            return Collections.emptyMap();
        }
        List<AuthUser> listResult = this.listUserInfoByIds(userNameList);
        if (Objects.isNull(listResult) || CollectionUtils.isEmpty(listResult)) {
            return Collections.emptyMap();
        }
        Map<String, UserInfo> result = new HashMap<>();

        listResult.stream().forEach(data ->{
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(data.getUserName());
            userInfo.setNickName(data.getNickName());
            userInfo.setAvatar(data.getAvatar());
            result.put(userInfo.getUserName(), userInfo);
        });

        return result;
    }
}
