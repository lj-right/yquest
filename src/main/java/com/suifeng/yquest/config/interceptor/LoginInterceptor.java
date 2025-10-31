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
            if (StpUtil.isLogin()) {
                String loginId = (String) StpUtil.getTokenInfo().loginId;
                if (loginId != null) {
                    LoginContextHolder.set("loginId", SaSecureUtil.md5BySalt(loginId, "suifeng"));
                    return true;
                }
            }
            String satoken = request.getHeader("token");
            if (StringUtils.isBlank(satoken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            String tokenKey = "satoken:login:token:" + satoken;
            if (redisTemplate.hasKey(tokenKey)) {
                Object tokenValue = redisTemplate.opsForValue().get(tokenKey);
                if (tokenValue.equals(-4)){
                    return false;
                }
                String loginId =(String) tokenValue;
                //把唯一的username加密后作为loginId
                LoginContextHolder.set("loginId", loginId);
                return true;
            }
            return false;

        } catch (Exception e) {
            log.error("登录拦截器处理异常", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LoginContextHolder.remove();
    }

}

