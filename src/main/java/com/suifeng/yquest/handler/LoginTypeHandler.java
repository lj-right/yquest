package com.suifeng.yquest.handler;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.suifeng.yquest.api.enums.LoginInfoTypeEnum;
import com.suifeng.yquest.entity.AuthUser;

public interface LoginTypeHandler {

    /**
     * 获取登录方式
     * @return
     */
    LoginInfoTypeEnum getLoginInfoType();

    /**
     * 登录
     * @param user
     * @return
     */
    SaTokenInfo login(AuthUser user);
}
