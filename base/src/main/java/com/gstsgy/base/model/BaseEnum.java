package com.gstsgy.base.model;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 枚举类的基类
 * @auther hujun
 * @create 2020-04-02 13:30
 */
public interface BaseEnum extends IEnum<Integer> {


    /**
     * 真正与数据库进行映射的值
     *
     * @return
     */
    Integer getValue();

    /**
     * 显示的信息
     *
     * @return
     */
    String getDisplayName();


//    //  动态添加方法  添加完后加入缓存 减少对枚举的修改
//    static BaseEnum addEnum(Class<? extends BaseEnum> enumType, String enumName, Integer value, String name) {
//        BaseEnum yesNoEnum = DynamicEnumUtil.addEnum(enumType, enumName, new Class[]{Integer.class, String.class}, new Object[]{value, name});
//        EnumContainer.setEnum(enumType, value, yesNoEnum);
//        return yesNoEnum;
//    }

    static String getDisplayName(Object value, Class<? extends BaseEnum> enumType) {
        String display = null;
        BaseEnum[] results = enumType.getEnumConstants();
        for (BaseEnum result : results) {
            if (result.getValue().equals(value)) {
                display = result.getDisplayName();
            }
        }
        return display;
    }

    static Map<Integer, String> getDisplayName(Class<? extends BaseEnum> enumType) {
        BaseEnum[] results = enumType.getEnumConstants();
        Map<Integer, String> valToDisplays = new HashMap<>();
        for (BaseEnum result : results) {
            valToDisplays.put(result.getValue(), result.getDisplayName());
        }
        return valToDisplays;
    }

    static Integer getValue(String display, Class<? extends BaseEnum> enumType) {
        Integer value = null;
        BaseEnum[] results = enumType.getEnumConstants();
        for (BaseEnum result : results) {
            if (result.getDisplayName().equals(display)) {
                value = result.getValue();
            }
        }
        return value;
    }

    static Map<String, Integer> getValue(Class<? extends BaseEnum> enumType) {
        BaseEnum[] results = enumType.getEnumConstants();
        Map<String, Integer> displayToval = new HashMap<>();
        for (BaseEnum result : results) {
            displayToval.put(result.getDisplayName(), result.getValue());
        }
        return displayToval;
    }

//    static <T> T getEnumByValue(Integer value, Class<? extends BaseEnum> enumType) {
//        return EnumContainer.getEnum(enumType, value);
//    }

    static Map<Integer, BaseEnum> getEnumByValue(Class<? extends BaseEnum> enumType) {
        BaseEnum[] results = enumType.getEnumConstants();
        Map<Integer, BaseEnum> valToEnum = new HashMap<>();
        for (BaseEnum result : results) {
            valToEnum.put(result.getValue(), result);
        }
        return valToEnum;
    }

    static BaseEnum getEnumByDisplay(String value, Class<? extends BaseEnum> enumType) {
        BaseEnum[] results = enumType.getEnumConstants();

        for (BaseEnum result : results) {
            if (result.getDisplayName().equals(value)) {
                return result;
            }
        }
        return null;
    }

    static Map<String, BaseEnum> getEnumByDisplay(Class<? extends BaseEnum> enumType) {
        BaseEnum[] results = enumType.getEnumConstants();
        Map<String, BaseEnum> valToEnum = new HashMap<>();
        for (BaseEnum result : results) {
            valToEnum.put(result.getDisplayName(), result);
        }
        return valToEnum;
    }



//    //  动态添加方法  添加完后加入缓存 减少对枚举的修改
//    default BaseEnum addEnum( String enumName, Integer value, String name) {
//        BaseEnum yesNoEnum = DynamicEnumUtil.addEnum(this.getClass(), enumName, new Class[]{Integer.class, String.class}, new Object[]{value, name});
//        EnumContainer.setEnum(this.getClass(), value, yesNoEnum);
//        return yesNoEnum;
//    }

    default String getDisplayName(Integer value) {
        String display = null;
        BaseEnum[] results = this.getClass().getEnumConstants();
        for (BaseEnum result : results) {
            if (result.getValue().equals(value)) {
                display = result.getDisplayName();
            }
        }
        return display;
    }

    default Map<Integer, String> getEnumMapVal2Label() {
        BaseEnum[] results = this.getClass().getEnumConstants();
        Map<Integer, String> valToDisplays = new HashMap<>();
        for (BaseEnum result : results) {
            valToDisplays.put(result.getValue(), result.getDisplayName());
        }
        return valToDisplays;
    }

    default Integer getValue(String display) {
        Integer value = null;
        BaseEnum[] results = this.getClass().getEnumConstants();
        for (BaseEnum result : results) {
            if (result.getDisplayName().equals(display)) {
                value = result.getValue();
            }
        }
        return value;
    }

    default Map<String, Integer> getEnumMapLabel2Val() {
        BaseEnum[] results = this.getClass().getEnumConstants();
        Map<String, Integer> displayToval = new HashMap<>();
        for (BaseEnum result : results) {
            displayToval.put(result.getDisplayName(), result.getValue());
        }
        return displayToval;
    }

//   // default  <T> T getEnumByValue(Integer value) {
//        return EnumContainer.getEnum(this.getClass(), value);
//    }

    default Map<Integer, BaseEnum> getEnumByValue() {
        BaseEnum[] results = this.getClass().getEnumConstants();
        Map<Integer, BaseEnum> valToEnum = new HashMap<>();
        for (BaseEnum result : results) {
            valToEnum.put(result.getValue(), result);
        }
        return valToEnum;
    }

    default BaseEnum getEnumByDisplay(String value) {
        BaseEnum[] results = this.getClass().getEnumConstants();

        for (BaseEnum result : results) {
            if (result.getDisplayName().equals(value)) {
                return result;
            }
        }
        return null;
    }

    default Map<String, BaseEnum> getEnumByDisplay() {
        BaseEnum[] results = this.getClass().getEnumConstants();
        Map<String, BaseEnum> valToEnum = new HashMap<>();
        for (BaseEnum result : results) {
            valToEnum.put(result.getDisplayName(), result);
        }
        return valToEnum;
    }
}
