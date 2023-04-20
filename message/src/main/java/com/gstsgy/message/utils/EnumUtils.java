package com.gstsgy.message.utils;

import com.gstsgy.base.model.BaseEnum;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/8/12 下午3:46
 */
public final class EnumUtils {
    private EnumUtils(){}

    public static int getCode(BaseEnum baseEnum){
        return baseEnum.getValue();
    }
}
