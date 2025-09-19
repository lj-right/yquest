package com.suifeng.yquest.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.suifeng.yquest.config.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * mvc的全局处理
 */
@Configuration
public class GlobalConfig extends WebMvcConfigurationSupport {

    /**
     * 通过继承WebMvcConfigurationSupport并重写configureMessageConverters方法，
     * 实现了全局的HTTP消息转换器配置，简化了后续开发中对JSON处理的配置需求。
     *
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        //MappingJackson2HttpMessageConverter添加到Spring MVC的消息转换器列表中,
        //确保在HTTP请求和响应中能够正确处理JSON数据。
        converters.add(mappingJackson2HttpMessageConverter());
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/authUser/auth/login",
//                        "/authUser/no-auth/register",
//                        "/authUser/email/send",
//                        "/authUser/auth/captcha",
//                        "/refer/searchByMessage",
//                        "/authUser/auth/isLogin",
//                        "/refer/queryPage");
    }

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        //ObjectMapper被配置为在序列化空对象时
        //不会抛出异常（FAIL_ON_EMPTY_BEANS设置为false），增强了系统的健壮性。
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
