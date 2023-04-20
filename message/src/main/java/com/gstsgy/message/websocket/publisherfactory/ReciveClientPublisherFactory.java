package com.gstsgy.message.websocket.publisherfactory;


import com.gstsgy.base.psp.Publisher;
import com.gstsgy.base.psp.Subscriber;
import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.message.bean.MessageBean;
import com.gstsgy.message.websocket.subscriber.ReciveClientSubscriber;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/11/15 上午10:18
 */
@Component
public class ReciveClientPublisherFactory implements Publisher<MessageBean, Object> {
    @Override
    public List<? extends Subscriber<MessageBean, Object>> getSubscriber() {
        return SpringUtils.getBeans(ReciveClientSubscriber.class);
    }
}
