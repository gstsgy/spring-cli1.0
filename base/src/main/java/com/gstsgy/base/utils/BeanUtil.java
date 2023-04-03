package com.gstsgy.base.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;

import java.lang.ref.SoftReference;
import java.util.*;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/2/22 下午2:35
 */
public abstract class BeanUtil {

    private static SoftReference<Map<BeanCopierKey, BeanCopier>> beanCopiers = new SoftReference<Map<BeanCopierKey, BeanCopier>>(new HashMap<>());

    private static final SelfConverter SELF_CONVERTER = new SelfConverter();

    public static BeanMap beanToMap(Object object) {
        return BeanMap.create(object);
    }

    public static <T> T mapToBean(Map map, Class<T> tClass) throws InstantiationException, IllegalAccessException {
        T t = tClass.newInstance();
        BeanMap beanMap = BeanMap.create(t);
        beanMap.putAll(map);
        return t;
    }

    public static void copy(Object source, Object target) {
        Map<BeanCopierKey, BeanCopier> map = beanCopiers.get();
        if (map == null) {
            map = new HashMap<>();
            beanCopiers = new SoftReference<>(map);
        }
        BeanCopierKey beanCopierKey = new BeanCopierKey(source.getClass(), target.getClass());
        BeanCopier beanCopier = map.get(beanCopierKey);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(source.getClass(), target.getClass(), true);
            map.put(beanCopierKey, beanCopier);
        }

        beanCopier.copy(source, target, SELF_CONVERTER);
    }

    static class SelfConverter implements Converter {
        public List<Class<?>> baseClass = Arrays.asList(int.class, byte.class, short.class, long.class, float.class, double.class, boolean.class, char.class);
        public List<Class<?>> baseWrapClass = Arrays.asList(Integer.class, Byte.class, Short.class, Long.class,
                Float.class, Double.class, Boolean.class, Character.class);
        public Map<Class, Class> wrap2Base = new HashMap<>();


        public SelfConverter() {
            wrap2Base.put(Integer.class, int.class);
            wrap2Base.put(Byte.class, byte.class);
            wrap2Base.put(Short.class, short.class);
            wrap2Base.put(Long.class, long.class);

            wrap2Base.put(Float.class, float.class);
            wrap2Base.put(Double.class, double.class);

            wrap2Base.put(Boolean.class, boolean.class);
            wrap2Base.put(Character.class, char.class);
        }

        @Override
        public Object convert(Object o, Class aClass, Object o1) {
            if (o == null) {
                return null;
            }
            if (aClass == String.class && o.getClass() == String.class) {
                return o;
            }
            if (aClass == String.class) {
                return String.valueOf(o);
            }
            // 都是包装类
            if (aClass == o.getClass() && SELF_CONVERTER.baseWrapClass.contains(aClass)) {
                return o;
            }
            // 都是基本类型
            if (aClass == o.getClass() && SELF_CONVERTER.baseClass.contains(aClass)) {
                return o;
            }
            // target 基础类型 且对应 value.getClass() 为包装类
            if (aClass == SELF_CONVERTER.wrap2Base.get(o.getClass()) && SELF_CONVERTER.baseWrapClass.contains(o.getClass())) {
                return o;
            }
            // value.getClass() 基础类型 且对应 target 为包装类
            if (o.getClass() == SELF_CONVERTER.wrap2Base.get(aClass) && SELF_CONVERTER.baseWrapClass.contains(aClass)) {
                return o;
            }
            return null;
        }
    }

    static class BeanCopierKey {
        private Class source;
        private Class target;

        public BeanCopierKey(Class source, Class target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BeanCopierKey that = (BeanCopierKey) o;
            return Objects.equals(source, that.source) && Objects.equals(target, that.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, target);
        }
    }

//    /**
//     * 类型转换
//     *
//     * @param value       转换的值
//     * @param targetClass 目标类型
//     * @return 转换后的值
//     */
//    public static Object convert(Object value, Class targetClass) {
//        if (value == null) {
//            return null;
//        }
//        if (targetClass == String.class && value.getClass() == String.class) {
//            return value;
//        }
//        if (targetClass == String.class) {
//            return String.valueOf(value);
//        }
//        // 都是包装类
//        if (targetClass == value.getClass() && SELF_CONVERTER.baseWrapClass.contains(targetClass)) {
//            return value;
//        }
//        // 都是基本类型
//        if (targetClass == value.getClass() && SELF_CONVERTER.baseClass.contains(targetClass)) {
//            return value;
//        }
//        // target 基础类型 且对应 value.getClass() 为包装类
//        if (targetClass == SELF_CONVERTER.wrap2Base.get(value.getClass()) && SELF_CONVERTER.baseWrapClass.contains(value.getClass())) {
//            return value;
//        }
//        // value.getClass() 基础类型 且对应 target 为包装类
//        if (value.getClass() == SELF_CONVERTER.wrap2Base.get(targetClass) && SELF_CONVERTER.baseWrapClass.contains(targetClass)) {
//            return value;
//        }
//        // 枚举类型
//        if (Arrays.asList(targetClass.getInterfaces()).contains(BaseEnum.class)) {
//            UniversalEnumConverterFactory converterFactory = new UniversalEnumConverterFactory();
//            org.springframework.core.convert.converter.Converter<String, BaseEnum> baseEnumConverter = converterFactory.getConverter1(targetClass);
//            return baseEnumConverter.convert(String.valueOf(value));
//        }
//        // value 是string target是包装类
//        if (targetClass == Integer.class || targetClass == int.class) {
//            return Integer.valueOf(String.valueOf(value));
//
//        } else if (targetClass == Long.class || targetClass == long.class) {
//            return Long.valueOf(String.valueOf(value));
//        } else if (targetClass == Double.class || targetClass == double.class) {
//            return Double.valueOf(String.valueOf(value));
//        } else if (targetClass == Float.class || targetClass == float.class) {
//            return Float.valueOf(String.valueOf(value));
//        } else if (targetClass == Byte.class || targetClass == byte.class) {
//            return Byte.valueOf(String.valueOf(value));
//        } else if (targetClass == Short.class || targetClass == short.class) {
//            return Short.valueOf(String.valueOf(value));
//        } else if (targetClass == Character.class || targetClass == char.class) {
//            return String.valueOf(value).charAt(0);
//        } else if (targetClass == Boolean.class || targetClass == boolean.class) {
//            return Boolean.valueOf(String.valueOf(value));
//        } else if (targetClass == BigDecimal.class) {
//            return new BigDecimal(String.valueOf(value));
//        }
//        throw new RuntimeException(String.format("类型转换异常，value类型为%s,目标类型为%s",value.getClass().getName(),targetClass.getName()));
//    }
}
