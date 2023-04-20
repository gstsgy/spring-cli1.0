package com.gstsgy.message.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname MessageBeanDO
 * @Description TODO
 * @Date 2021/2/26 上午10:20
 * @Created by guyue
 */
@Data
public class MessageBean {
    private long messageId;
    private Long sender;
    private Long recipient;
    private SendTypeEnum sendType;
    private int messageType;
    private int status;
    private String title;
    private Object message;
    private LocalDateTime sendDate;
    private LocalDateTime creatDate;
    private LocalDateTime readDate;
}
