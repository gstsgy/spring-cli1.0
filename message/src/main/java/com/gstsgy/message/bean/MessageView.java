package com.gstsgy.message.bean;

import lombok.Data;

/**
 * @ClassName WebSocketMessage
 * @Description TODO
 * @Author guyue
 * @Date 2020/9/9 下午4:03
 **/
@Data
public class MessageView {
    private int messageType;

    private String title;

    private Object message;

    public MessageView(){

    }

    public MessageView(MessageBean messageBean){
        this.messageType = messageBean.getMessageType();
        this.message = messageBean.getMessage();
        this.title = messageBean.getTitle();
    }

}
