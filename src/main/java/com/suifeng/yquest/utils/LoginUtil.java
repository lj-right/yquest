package com.suifeng.yquest.utils;

import com.suifeng.yquest.context.LoginContextHolder;

/**
 * 用户登录util
 */
public class LoginUtil {
    public static String getLoginId(){
        return LoginContextHolder.getLoginId();
    }

}
