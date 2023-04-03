package com.gstsgy.export.function;


import com.gstsgy.base.model.ResponseBean;

import java.util.List;

public class DefaultParseResponseFun implements ParseResponseFun {
    @Override
    public List<Object> parse(Object obj) {
        if (obj instanceof List list) {
            return list;
        }
        if (obj instanceof ResponseBean responseBean) {
            if (responseBean.getData() != null) {
                if (responseBean.getData() instanceof List list) {
                    return list;
                }
            }
        }
        return null;
    }
}
