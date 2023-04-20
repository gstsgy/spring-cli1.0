package com.gstsgy.message;


import com.gstsgy.message.bean.SendTypeEnum;
import com.gstsgy.message.websocket.WebSocketBroadSend;
import com.gstsgy.message.websocket.WebSocketSend;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname FactorySend
 * @Description TODO
 * @Date 2021/2/26 下午4:13
 * @Created by guyue
 */
 public class FactorySend {
    private static Map<SendTypeEnum, Send> map = new HashMap<>();

    static {
        map.put(SendTypeEnum.WEBSOCKET,new WebSocketSend());
        map.put(SendTypeEnum.WEBSOCKET_BROAD,new WebSocketBroadSend());
        map.put(SendTypeEnum.WEBSOCKET_INIT,new WebSocketBroadSend());
    }

    public static Send getSend(SendTypeEnum sendTypeEnum){
        return map.get(sendTypeEnum);
    }
}
