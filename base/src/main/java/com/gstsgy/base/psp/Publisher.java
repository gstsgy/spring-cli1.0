package com.gstsgy.base.psp;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/11/2 下午1:12
 */
public interface Publisher<T, E> {
    List<? extends Subscriber<T, E>> getSubscriber();

    default void notifyExecute(T object, Consumer<E> consumer) {
        List<? extends Subscriber<T, E>> subscribers = getSubscriber();
        if (subscribers != null) {
            subscribers.stream().filter(it -> !SubscriberConf.isSubscriberCancel(it.getClass())).forEach(item -> {
                item.execute(object, consumer);
            });
        }
    }

    default void notifyExecuteBefore(T object) {
        List<? extends Subscriber<T, E>> subscribers = getSubscriber();
        if (subscribers != null) {
            subscribers.stream().filter(it -> !SubscriberConf.isSubscriberCancel(it.getClass())).forEach(item -> {
                item.executeBefore(object);
            });
        }
    }

    default void notifyExecuteAfter(T object) {
        List<? extends Subscriber<T, E>> subscribers = getSubscriber();
        if (subscribers != null) {
            subscribers.stream().filter(it -> !SubscriberConf.isSubscriberCancel(it.getClass())).forEach(item -> {
                item.executeAfter(object);
            });
        }
    }
}
