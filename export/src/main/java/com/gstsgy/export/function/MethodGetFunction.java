package com.gstsgy.export.function;

import com.gstsgy.export.utils.GetMethodUtils;
import com.gstsgy.tools.excel.function.GetFunction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodGetFunction implements GetFunction <Object, Object>,Cloneable{


    public String fieldName;

    public Class  targetType;

    public Class  controllerType;

    @Override
    public Object getVal(Object o){
        try {
            Method getMethod = GetMethodUtils.getMethodByFieldName(targetType,fieldName);
            return getMethod.invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    public  MethodGetFunction clone() throws CloneNotSupportedException {
      return (MethodGetFunction) super.clone();
    }

}
