package com.gstsgy.message.bean;
import com.gstsgy.base.model.BaseEnum;

/**
 * @Classname SendType
 * @Description TODO
 * @Date 2021/2/26 上午11:24
 * @Created by guyue
 */
public enum SendTypeEnum implements BaseEnum {
    WEBSOCKET(1, "WEBSOCKET"),
    EMAIL(2, "EMAIL"),
    SMS(3, "SMS"),
    PHONE(4, "PHONE"),
    WEBSOCKET_BROAD(5, "WEBSOCKET广播"),
    WEBSOCKET_INIT(6, "WEBSOCKET初始化数据"),
    ;

    private Integer value;
    private String disPlay;

    private SendTypeEnum(Integer value, String disPlay) {
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
