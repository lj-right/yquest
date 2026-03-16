package com.suifeng.yquest.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.suifeng.yquest.api.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 未登录
    @ExceptionHandler(NotLoginException.class)
    public Result handleNotLogin(NotLoginException e) {
        return Result.fail(401, "未登录或Token已过期");
    }

    // 无权限
    @ExceptionHandler(NotPermissionException.class)
    public Result handleNoPerm(NotPermissionException e) {
        return Result.fail(403, "无权限：" + e.getMessage());
    }

    // 无角色
    @ExceptionHandler(NotRoleException.class)
    public Result handleNoRole(NotRoleException e) {
        return Result.fail(403, "无角色权限：" + e.getMessage());
    }

    // 其他Sa-Token异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return Result.fail(500, "服务器异常：" + e.getMessage());
    }
}