package com.gstsgy.sequence.util;

/**
 * @Classname IdWorker
 * @Description TODO
 * @Date 2020/12/28 上午11:10
 * @Created by guyue
 */

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneOffset;

public final class SnowFlake {
    private SnowFlake(){}

    // 序列号识位数
    private final static long sequenceBits = 8L;

    // 序列号ID最大值
    private final static long sequenceMask = ~(-1L << sequenceBits); // 0b 1111 1111


    // 时间毫秒左移10位  8位序列号 + 2位机器码
    private final static long timestampLeftShift = sequenceBits + 2;

    private static long lastTimestamp = -1L;

    private static long sequence = 0L;

    private static long basisDate = 1577808000000L; ////2020-01-01 00:00:00

    public static void setBasisDate(LocalDate basisDate) {
        SnowFlake.basisDate=basisDate.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    private static long machineId = 0L; // 机器码默认为0 最大为3 0 1 2 3

    public static void setMachineId(long machineId) {
        SnowFlake.machineId = machineId & 3<<sequenceBits;
    }


    public static long generate() {
        return nextId();
    }

//    public static void main(String[] args) {
//        System.out.println(sequenceBits);
//        System.out.println(sequenceMask);
//        System.out.println(-1 << 8);
//    }

    /**
     * 实际产生代码的
     *
     * @return
     */
    private static synchronized long nextId() {

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


        return ((timestamp - basisDate) << timestampLeftShift) |machineId| sequence;
    }

    // 防止产生的时间比之前的时间还要小（由于NTP回拨等问题）,保持增量的趋势.
    private static long tailNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    // 获取当前的时间戳
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
