package com.gstsgy.message.bean;


import com.gstsgy.base.model.BaseEnum;

/**
 * @Classname MessageStatusEnum
 * @Description TODO
 * @Date 2021/2/26 上午11:39
 * @Created by guyue
 */
public enum MessageStatusEnum implements BaseEnum {
    CREATE(1, "已创建"),
    SENDED(2, "已发送"),
    READED(3, "已读");

    private Integer value;
    private String disPlay;

    private MessageStatusEnum(Integer value, String disPlay) {
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
