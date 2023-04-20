package com.gstsgy.message;

import com.gstsgy.message.bean.MessageBean;
import com.gstsgy.message.bean.SendTypeEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname MessageContainer
 * @Description TODO
 * @Date 2021/2/26 下午1:16
 * @Created by guyue
 */
public class MessageContainer {
    private volatile static List<MessageBean> container = new ArrayList<>();

    public static void add(MessageBean messageBean) {
        if (messageBean.getSendDate() != null && messageBean.getSendDate().isAfter(LocalDateTime.now())) {
            synchronized (MessageContainer.class) {
                container.add(messageBean);
            }
        }
        if(messageBean.getSendType().equals(SendTypeEnum.WEBSOCKET_INIT)){
            synchronized (MessageContainer.class) {
                container = container.stream().filter(it -> it.getSendType().equals(SendTypeEnum.WEBSOCKET_INIT)).
                        // 过滤出不等于该 messageType的类型 即该类型数据重置
                        filter(it -> it.getMessageType()!=messageBean.getMessageType())
                        .collect(Collectors.toList());
                container.add(messageBean);
            }
        }
    }

    public static List<MessageBean> getCanSendMessage() {
        List<MessageBean> canMessages = container.stream().filter(it -> it.getSendDate().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        synchronized (MessageContainer.class) {
            container = container.stream().filter(it -> it.getSendDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        }
        return canMessages;
    }
    public static List<MessageBean> getWebSocketInitMessage() {
        return container.stream().filter(it -> it.getSendType().equals(SendTypeEnum.WEBSOCKET_INIT)).collect(Collectors.toList());
    }



    public static List<MessageBean> getAllMessages() {
        return container;
    }

    public static void clean() {
        synchronized (MessageContainer.class) {
            container.clear();
        }

    }
}
