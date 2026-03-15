package com.suifeng.yquest.config.interceptor;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.suifeng.yquest.config.context.LoginContextHolder;
import com.suifeng.yquest.config.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate= RedisUtil.redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // 1. 校验是否登录 —— 判断是否登录（Sa-Token 自动查 Redis、验Token、验过期）
            StpUtil.checkLogin();

            // 2. 获取登录ID（安全获取）
            String loginId = (String) StpUtil.getLoginId();

            // 3. 存入上下文（你项目统一格式即可，不要一会加密一会不加密）
            LoginContextHolder.set("loginId", SaSecureUtil.md5BySalt(loginId, "suifeng"));

            return true;

        } catch (Exception e) {
            log.error("登录拦截器校验失败: {}", e.getMessage());
            // 未登录 / token 无效
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LoginContextHolder.remove();
    }

}

