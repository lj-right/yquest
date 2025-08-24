package com.suifeng.yquest.handler;

import com.suifeng.yquest.api.enums.LoginInfoTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginTypeHandlerFactory implements InitializingBean {

    @Resource
    private List<LoginTypeHandler> loginTypeHandlerList;

    private Map<LoginInfoTypeEnum, LoginTypeHandler> handlerMap = new HashMap<>();

    public LoginTypeHandler getHandler(int loginType) {
        LoginInfoTypeEnum loginInfoTypeEnum = LoginInfoTypeEnum.getByCode(loginType);
        return handlerMap.get(loginInfoTypeEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loginTypeHandlerList.forEach(
                loginTypeHandler -> handlerMap.put(loginTypeHandler.getLoginInfoType(), loginTypeHandler)
        );
    }
}
