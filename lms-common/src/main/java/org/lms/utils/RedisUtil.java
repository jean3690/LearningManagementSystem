package org.lms.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class RedisUtil {
    public static <T> Map<String,String> objToHash(T obj){
        return JsonUtil.jsonToObj(JsonUtil.toJson(obj), new TypeReference<Map<String, String>>(){});
    }
    public static <T> T hashToObj(Map<String,String> map,Class<?> clazz){
        String obj = JsonUtil.toJson(map);
        return (T) JsonUtil.jsonToObj(obj,clazz);
    }
}
