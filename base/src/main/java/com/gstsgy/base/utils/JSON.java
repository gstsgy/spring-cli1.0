package com.gstsgy.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSON {
    private JSON() {

    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return obj.toString();
        }
    }

    public static<T> T parseObject(String json,Class<T> tClass){
        try {
            return objectMapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
