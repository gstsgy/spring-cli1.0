package com.gstsgy.base.psp;

import org.springframework.util.ClassUtils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class SubscriberConf {
    private static Set<Class<? extends Subscriber>> subscribersCancelList = new CopyOnWriteArraySet<>();

    public static void subscriberCancel(Class<? extends Subscriber> subscriber){
        subscribersCancelList.add(subscriber);
    }

    public static boolean isSubscriberCancel(Class<? extends Subscriber> subscriber){
        return subscribersCancelList.contains(ClassUtils.getUserClass(subscriber));
    }
}
