package org.lms.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

@Slf4j
public class JsonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    public static <T> String toJson(T obj){
        if(obj==null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error:{}",e.getMessage());
            return null;
        }
    }
    public static <T> T jsonToObj(String str,Class<T> clazz){
        if(!StringUtils.hasText(str)||clazz==null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str :objectMapper.readValue(str,clazz);
        } catch (JsonProcessingException e) {
            log.warn("Parse string to obj error:{}",e.getMessage());
            return null;
        }
    }
    public static <T> T jsonToObj(String str, TypeReference<T> tTypeReference){
        if(!StringUtils.hasText(str)||tTypeReference==null){
            return null;
        }
        try {
            return (T) (tTypeReference.getType().equals(String.class)?str:objectMapper.readValue(str,tTypeReference));
        } catch (JsonProcessingException e) {
            log.warn("Parse string to obj error:{}",e.getMessage());
            return null;
        }
    }
    public static <T> T jsonToObj(String str,Class<?> collectionClazz,Class<?>...elementClazzes){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (JsonProcessingException e) {
            log.warn("Parse string to obj error:{}",e.getMessage());
            return null;
        }
    }
}
