package com.gstsgy.export.service.impl;

import com.gstsgy.export.function.DefaultParseResponseFun;
import com.gstsgy.export.function.MethodGetFunction;
import com.gstsgy.export.function.ParseResponseFun;


public abstract class ExcportConfUtils {
    protected static ParseResponseFun parseResponseFun = new DefaultParseResponseFun();

    protected static MethodGetFunction getFunction = new MethodGetFunction() ;

    public static void   setParseResponseFun(ParseResponseFun parseResponseFun){
        ExcportConfUtils.parseResponseFun = parseResponseFun;
    }

    public static void setGetFunction(MethodGetFunction getFunction){
        ExcportConfUtils.getFunction = getFunction;
    }
}
