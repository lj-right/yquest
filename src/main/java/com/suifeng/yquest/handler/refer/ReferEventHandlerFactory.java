package com.suifeng.yquest.handler.refer;

import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内推状态机事件处理器工厂（纯状态转移事件无需注册处理器）
 */
@Component
public class ReferEventHandlerFactory implements InitializingBean {

    @Resource
    private List<ReferEventHandler> referEventHandlerList;

    private Map<ReferApplyEventEnum, ReferEventHandler> handlerMap = new HashMap<>();

    public ReferEventHandler getHandler(ReferApplyEventEnum event) {
        return handlerMap.get(event);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        referEventHandlerList.forEach(
                referEventHandler -> handlerMap.put(referEventHandler.getEvent(), referEventHandler)
        );
    }
}
