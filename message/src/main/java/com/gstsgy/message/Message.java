package com.gstsgy.message;


import com.gstsgy.message.bean.MessageBean;
import com.gstsgy.message.bean.MessageStatusEnum;
import com.gstsgy.message.bean.MessageTypeEnum;
import com.gstsgy.message.bean.SendTypeEnum;
import com.gstsgy.sequence.SeqUtils;

import java.time.LocalDateTime;

/**
 * @Classname Message
 * @Description TODO
 * @Date 2021/2/26 下午1:13
 * @Created by guyue
 */
public class Message {



    private static void send(MessageBean messageBean) {
        if (messageBean.getSendDate() != null && messageBean.getSendDate().isAfter(LocalDateTime.now())) {
            MessageContainer.add(messageBean);
            return;
        }
        if (messageBean.getSendType() .equals(SendTypeEnum.WEBSOCKET_INIT)) {
            MessageContainer.add(messageBean);
        }
        Send send = FactorySend.getSend(messageBean.getSendType());
        messageBean.setStatus(MessageStatusEnum.SENDED.getValue());
        messageBean.setSendDate(LocalDateTime.now());
        send.sendMessage(messageBean);
    }

    public static void send(Long sender, Long recipient, SendTypeEnum sendType, MessageTypeEnum messageType, String title,
                            Object message, LocalDateTime sendDate) {
        send(sender, recipient, sendType, messageType.getValue(), title,
                message, sendDate);
    }

    public static void send(Long recipient, SendTypeEnum sendType, MessageTypeEnum messageType, String title, Object message, LocalDateTime sendDate) {
        send(null, recipient, sendType, messageType, title,
                message, sendDate);
    }
    public static void send(Long recipient, SendTypeEnum sendType, MessageTypeEnum messageType, String title, Object message) {
        send(null, recipient, sendType, messageType, title,
                message, null);
    }
    public static void send( SendTypeEnum sendType, MessageTypeEnum messageType, String title, Object message) {
        send(null, null, sendType, messageType, title,
                message, null);
    }
    public static void send(MessageTypeEnum messageType, String title, Object message, LocalDateTime sendDate) {
        send(null, null, SendTypeEnum.WEBSOCKET_BROAD, messageType, title,
                message, sendDate);
    }

    public static void send(MessageTypeEnum messageType, String title, Object message) {
        send(null, null, SendTypeEnum.WEBSOCKET_BROAD, messageType, title,
                message, null);
    }

    public static void send(Long sender, Long recipient, SendTypeEnum sendType, int messageType, String title,
                            Object message, LocalDateTime sendDate) {
        MessageBean messageBean = new MessageBean();
        messageBean.setMessageId(SeqUtils.getSeq());
        messageBean.setSender(sender);
        messageBean.setRecipient(recipient);
        messageBean.setSendType(sendType);
        messageBean.setMessageType(messageType);
        messageBean.setStatus(MessageStatusEnum.CREATE.getValue());
        messageBean.setTitle(title);
        messageBean.setMessage(message);
        messageBean.setSendDate(sendDate);
        messageBean.setCreatDate(LocalDateTime.now());
        send(messageBean);
    }

    public static void send(Long recipient, SendTypeEnum sendType, int messageType, String title, Object message, LocalDateTime sendDate) {
        send(null, recipient, sendType, messageType, title,
                message, sendDate);
    }
    public static void send(Long recipient, SendTypeEnum sendType, int messageType, String title, Object message) {
        send(null, recipient, sendType, messageType, title,
                message, null);
    }
    public static void send( SendTypeEnum sendType, int messageType, String title, Object message) {
        send(null, null, sendType, messageType, title,
                message, null);
    }
    public static void send(int messageType, String title, Object message, LocalDateTime sendDate) {
        send(null, null, SendTypeEnum.WEBSOCKET_BROAD, messageType, title,
                message, sendDate);
    }

    public static void send(int messageType, String title, Object message) {
        send(null, null, SendTypeEnum.WEBSOCKET_BROAD, messageType, title,
                message, null);
    }
}
