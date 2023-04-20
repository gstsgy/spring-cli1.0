package com.gstsgy.message.websocket.conf;


import com.gstsgy.message.bean.MessageView;
import com.gstsgy.message.websocket.WsSessionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;

//@Component
public class WebsocketHeartCheck implements DisposableBean, InitializingBean {

    private volatile boolean isEnd;
    @Override
    public void destroy() throws Exception {
        isEnd = true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        heartCheck();
    }
    @Async
    public void heartCheck(){
        long now = System.currentTimeMillis();
        while (!isEnd){
            if(System.currentTimeMillis()-now>1000*10){
                MessageView messageView = new MessageView();
                messageView.setMessageType(999);
                WsSessionManager.broad(messageView);
                now = System.currentTimeMillis();
            }
        }
    }
}
