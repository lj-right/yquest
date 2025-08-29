package com.suifeng.yquest.handler;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.suifeng.yquest.api.enums.LoginInfoTypeEnum;
import com.suifeng.yquest.entity.AuthUser;
import com.suifeng.yquest.config.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class LoginByEmail implements LoginTypeHandler{
    private static final String LOGIN_PREFIX = "loginCode";

    @Resource
    private RedisUtil redisUtil;

    @Override
    public LoginInfoTypeEnum getLoginInfoType() {
        return LoginInfoTypeEnum.LOGIN_BY_EMAIL;
    }

    @Override
    public SaTokenInfo login(AuthUser user) {
        //这里用我们留存的ExtJson来验证码是否正确
        if (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getExtJson())) {
            return null;
        }
        String tEmail = redisUtil.buildKey(LOGIN_PREFIX, user.getEmail());
        String code = redisUtil.get(tEmail);
        if (StringUtils.isBlank(code)) {
            return null;
        } else if (code.equals(user.getExtJson())) {
            StpUtil.login(user.getEmail()); //以email作为唯一的标识
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            log.info("登录成功！token{}", tokenInfo.getTokenValue());
            return tokenInfo;
        }
        return null;
    }
}
