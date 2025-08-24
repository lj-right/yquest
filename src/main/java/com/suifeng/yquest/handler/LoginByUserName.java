package com.suifeng.yquest.handler;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.suifeng.yquest.api.enums.LoginInfoTypeEnum;
import com.suifeng.yquest.dao.AuthUserDao;
import com.suifeng.yquest.entity.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class LoginByUserName implements LoginTypeHandler{
    private static final String salt = "suifeng";

    @Resource
    private AuthUserDao userDao;

    @Override
    public LoginInfoTypeEnum getLoginInfoType() {
        return LoginInfoTypeEnum.LOGIN_BY_NAME;
    }

    @Override
    public SaTokenInfo login(AuthUser user) {
        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())) {
            return null;
        }
        AuthUser checkUser = new AuthUser();
        checkUser.setUserName(SaSecureUtil.md5BySalt(user.getUserName(),salt));
        AuthUser result = userDao.queryByName(checkUser);
        if (SaSecureUtil.md5BySalt(user.getPassword(),salt).equals(result.getPassword())) {
            StpUtil.login(result.getEmail()); //以email作为唯一的标识
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            log.info("登录成功！token{}", tokenInfo.getTokenValue());
            return tokenInfo;
        }
        return null;
    }
}
