package com.gstsgy.message.websocket;


import com.gstsgy.message.Send;
import com.gstsgy.message.bean.MessageBean;
import org.springframework.scheduling.annotation.Async;

/**
 * @Classname WebSocketSend
 * @Description TODO
 * @Date 2021/2/26 下午3:53
 * @Created by guyue
 */
public class WebSocketSend implements Send {
    @Async
    @Override
    public void sendMessage(MessageBean messageBean) {
      boolean flag=  WsSessionManager.sendMessage(messageBean.getRecipient(),messageBean);
      /*if(!flag){

      }*/
    }
}
