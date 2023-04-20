package com.gstsgy.message.bean;

import com.gstsgy.base.model.BaseEnum;

/**
 * @Classname MessageTypeEnum
 * @Description TODO
 * @Date 2021/2/26 上午11:43
 * @Created by guyue
 */
public enum MessageTypeEnum implements BaseEnum {
    STORAGE_ALL(11, "货位全量数据"),
    STORAGE_UPDATE(12, "货位变量数据"),
    WARNING_ALL(21, "预警全量数据"),
    WARNING_ADD(22, "预警增量数据"),
    ;

    private Integer value;
    private String disPlay;

    private MessageTypeEnum(Integer value, String disPlay) {
        this.value = value;
        this.disPlay = disPlay;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDisplayName() {
        return disPlay;
    }
}
