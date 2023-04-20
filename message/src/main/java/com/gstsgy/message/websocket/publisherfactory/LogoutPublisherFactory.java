package com.gstsgy.message.websocket.publisherfactory;


import com.gstsgy.base.psp.Publisher;
import com.gstsgy.base.psp.Subscriber;
import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.message.websocket.subscriber.LogoutSubscriber;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/11/15 上午10:18
 */
@Component
public class LogoutPublisherFactory implements Publisher<Long, Object> {
    @Override
    public List<? extends Subscriber<Long, Object>> getSubscriber() {
        return SpringUtils.getBeans(LogoutSubscriber.class);
    }
}
