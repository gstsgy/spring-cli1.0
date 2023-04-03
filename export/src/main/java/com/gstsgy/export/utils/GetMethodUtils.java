package com.gstsgy.export.utils;

import com.gstsgy.export.bean.WebColumn;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Objects;

public abstract class GetMethodUtils {

    public static Method getMethodByFieldName(Class type,String name){
        PropertyDescriptor[] descriptors = ReflectUtils.getBeanGetters(type);
        for(PropertyDescriptor propertyDescriptor : descriptors){
            if(Objects.equals(propertyDescriptor.getName(),name)){
                return propertyDescriptor.getReadMethod();
            }
        }
        return null;
    }
}
