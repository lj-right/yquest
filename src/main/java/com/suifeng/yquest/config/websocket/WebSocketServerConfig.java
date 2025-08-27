package com.suifeng.yquest.config.websocket;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketServerConfig extends ServerEndpointConfig.Configurator {

    @Override
    public boolean checkOrigin(String originHeaderValue) {
        return true;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> parameterMap = request.getParameterMap();
        List<String> erpList = parameterMap.get("erp");
        if(!CollectionUtils.isEmpty(erpList)){
            sec.getUserProperties().put("erp", erpList.get(0));
        }
//        sec.getUserProperties().put("erp", LoginUtil.getLoginId());
    }

}
