package com.suifeng.yquest.utils;

import com.suifeng.yquest.config.context.LoginContextHolder;

/**
 * 用户登录util
 */
public class LoginUtil {
    public static String getLoginId(){
        return LoginContextHolder.getLoginId();
    }

}
