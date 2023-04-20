package com.gstsgy.message.websocket.conf;

/*
 *
 * @ClassName HttpAuthHandler
 * @Description TODO
 * @Author guyue
 * @Date 2020/9/9 下午3:30
 *
 */
import com.gstsgy.base.utils.JSON;
import com.gstsgy.message.FactorySend;
import com.gstsgy.message.MessageContainer;
import com.gstsgy.message.Send;
import com.gstsgy.message.bean.MessageBean;
import com.gstsgy.message.bean.SendTypeEnum;
import com.gstsgy.message.websocket.WsSessionManager;
import com.gstsgy.message.websocket.publisherfactory.LoginPublisherFactory;
import com.gstsgy.message.websocket.publisherfactory.LogoutPublisherFactory;
import com.gstsgy.message.websocket.publisherfactory.ReciveClientPublisherFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
class HttpAuthHandler extends TextWebSocketHandler {

    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long user = (Long) session.getAttributes().get("user");
        if (user != null) {
            WsSessionManager.add(user, session);
            init(user);
            // 登录通知
            loginPublisherFactory.notifyExecute(user,null);

        } else {
            throw new RuntimeException("用户登录已经失效!");
        }
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Autowired
    ReciveClientPublisherFactory reciveClientPublisherFactory;
    @Autowired
    LogoutPublisherFactory logoutPublisherFactory;
    @Autowired
    LoginPublisherFactory loginPublisherFactory;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Long token = (Long) session.getAttributes().get("user");
        if(StringUtils.hasText(payload)){
            try{
                MessageBean messageBean = JSON.parseObject(payload,MessageBean.class);
                messageBean.setSender(token);
                reciveClientPublisherFactory.notifyExecute(messageBean,null);
            }catch (Exception exception){
                exception.printStackTrace();
            }

        }
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long user = (Long) session.getAttributes().get("user");
        if (user != null) {
            // 登出通知
            logoutPublisherFactory.notifyExecute(user,null);
            // 用户退出，移除缓存
            WsSessionManager.remove(user);
        }
    }


    private void init(long user) {
        // 初始化
        List<MessageBean> list = MessageContainer.getWebSocketInitMessage();
        list.forEach(it->it.setRecipient(user));
        Send send = FactorySend.getSend(SendTypeEnum.WEBSOCKET);
        list.forEach(send::sendMessage);
    }
}
