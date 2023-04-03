package com.gstsgy.base.utils;

/**
 * @Classname IdWorker
 * @Description TODO
 * @Date 2020/12/28 上午11:10
 * @Created by guyue
 */
import java.security.SecureRandom;

public class CustomUUID{

    // 序列号识位数
    private final static long sequenceBits = 8L;

    // 序列号ID最大值
    private final static long sequenceMask = ~(-1L << sequenceBits);

    // 时间毫秒左移23位
    private final static long timestampLeftShift = sequenceBits;

    private static long lastTimestamp = -1L;

    private long sequence = 0L;

    private CustomUUID() {
    }

    // 单例模式

    private static CustomUUID instance;

    public static CustomUUID getInstance(){
        if(instance!=null){
            return instance;
        }
        synchronized (CustomUUID.class){
            if(instance!=null){
                return instance;
            }
            instance = new CustomUUID();
            return instance;
        }
    }

    public long generate() {
        return this.nextId();
    }

    /**
     * 实际产生代码的
     *
     * @return
     */
    private synchronized long nextId() {

        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有10bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过1024，当为1024时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                //自旋等待到下一毫秒
                timestamp = tailNextMillis(lastTimestamp);
            }
        } else {
            // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加,
            // 为了保证尾数随机性更大一些,最后一位设置一个随机数
            sequence = new SecureRandom().nextInt(10);
        }

        lastTimestamp = timestamp;

        // 基准时间
        //2020-01-01 00:00:00
        long twepoch = 1577808000000L;
        return ((timestamp - twepoch) << timestampLeftShift) | sequence;
    }

    // 防止产生的时间比之前的时间还要小（由于NTP回拨等问题）,保持增量的趋势.
    private long tailNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    // 获取当前的时间戳
    protected long timeGen() {
        return System.currentTimeMillis();
    }

}
