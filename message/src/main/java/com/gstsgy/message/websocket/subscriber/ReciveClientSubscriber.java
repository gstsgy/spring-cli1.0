package com.gstsgy.message.websocket.subscriber;

import com.gstsgy.base.psp.Subscriber;
import com.gstsgy.message.bean.MessageBean;

/**
 * @author guyue  web socket 接收消息事件
 * @version 3.0
 * @description: TODO
 * @date 2021/11/15 上午10:16
 */
public interface ReciveClientSubscriber extends Subscriber<MessageBean,Object> {
}
