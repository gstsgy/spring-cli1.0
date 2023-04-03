package com.gstsgy.base.psp;


import java.util.function.Consumer;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/8/26 下午2:57
 */
public interface Subscriber<T,E> {

    /**
     * 执行前
     * @param params 执行参数
     */
    void executeBefore(T params );
    /**
     *
     * @param params 执行参数
     * @param consumer 执行结果
     */
    void execute(T params, Consumer<E> consumer );

    /**
     * 执行后
     * @param params 执行参数
     */
    void executeAfter(T params);
}
