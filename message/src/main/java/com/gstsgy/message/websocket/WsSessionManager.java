package com.gstsgy.message.websocket;

/**
 * @ClassName WsSessionManager
 * @Description TODO
 * @Author guyue
 * @Date 2020/9/9 下午3:31
 **/


import com.gstsgy.base.utils.JSON;
import com.gstsgy.message.bean.MessageBean;
import com.gstsgy.message.bean.MessageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buhao
 * @version WsSessionManager.java, v 0.1 2019-10-22 10:24 buhao*/


@Slf4j
public class WsSessionManager {

/**
     * 保存连接 session 的地方*/


    private static ConcurrentHashMap<Long, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

/**
     * 添加 session
     *
     * @param key*/


    public static void add(Long key, WebSocketSession session) {
        if(SESSION_POOL.get(key)!=null){
            removeAndClose(key);
        }
        // 添加 session
        SESSION_POOL.put(key, session);
    }

/**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return*/


    public static WebSocketSession remove(Long key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

/**
     * 删除并同步关闭连接
     *
     * @param key*/


    //@Async
    public static void removeAndClose(Long key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                // todo: 关闭出现异常处理
                e.printStackTrace();
            }
        }
    }

/**
     * 获得 session
     *
     * @param key
     * @return*/


    public static WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

    public static void broad(MessageView messageView) {

        WebSocketMessage textMessage = new TextMessage(JSON.toJson(messageView));
        SESSION_POOL.keySet().forEach(item -> {
            try {
                SESSION_POOL.get(item).sendMessage(textMessage);
            } catch (IOException e) {

            }
        });
    }


    public static boolean sendMessage(Long sessid, MessageView messageView) {

         if(SESSION_POOL.get(sessid)==null)return false;


        WebSocketMessage<String> textMessage = new TextMessage(JSON.toJson(messageView));
        try {
            SESSION_POOL.get(sessid).sendMessage(textMessage);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean sendMessage(Long sessid, MessageBean messageBean) {

        if(SESSION_POOL.get(sessid)==null)return false;


        WebSocketMessage<String> textMessage = new TextMessage(JSON.toJson(messageBean));
        try {
            SESSION_POOL.get(sessid).sendMessage(textMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
