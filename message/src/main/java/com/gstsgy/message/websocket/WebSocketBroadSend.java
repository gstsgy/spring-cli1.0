package com.gstsgy.message.websocket;


import com.gstsgy.message.Send;
import com.gstsgy.message.bean.MessageBean;
import com.gstsgy.message.bean.MessageView;

/**
 * @Classname WebSocketBroadSend
 * @Description TODO
 * @Date 2021/2/26 下午4:08
 * @Created by guyue
 */
public class WebSocketBroadSend implements Send {
    @Override
    public void sendMessage(MessageBean messageBean) {
        WsSessionManager.broad(new MessageView(messageBean));
    }
}
