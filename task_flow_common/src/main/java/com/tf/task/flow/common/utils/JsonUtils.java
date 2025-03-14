package com.tf.task.flow.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author ouweijian
 * @date 2024/11/7 22:43
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper objectMapper = new ObjectMapper();;

    static {
        // 忽略值为 null 的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 配置日期格式化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // 对象转 JSON 字符串
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error serializing object to JSON", e);
            throw new IllegalArgumentException("Error serializing object to JSON", e);
        }
    }

    // JSON 字符串转对象
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON to object", e);
            throw new IllegalArgumentException("Error deserializing JSON to object", e);
        }
    }

    // JSON 字符串转对象
    public static <T> T fromJson(String json, Type genericType) {
        try {
            // 使用 TypeFactory 来从 Type 创建 JavaType
            JavaType javaType = objectMapper.getTypeFactory().constructType(genericType);
            // 使用 Jackson 将 JSON 字符串反序列化为泛型对象
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON to object", e);
            throw new IllegalArgumentException("Error deserializing JSON to object", e);
        }
    }

    // JSON 字符串转泛型对象
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON to generic object", e);
            throw new IllegalArgumentException("Error deserializing JSON to generic object", e);
        }
    }

    // JSON 字符串转泛型 List
    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON to List", e);
            throw new IllegalArgumentException("Error deserializing JSON to List", e);
        }
    }

    // JSON 字符串转泛型 Map
    public static <K, V> Map<K, V> fromJsonToMap(String json, Class<K> keyClazz, Class<V> valueClazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructMapType(
                    Map.class, keyClazz, valueClazz);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON to Map", e);
            throw new IllegalArgumentException("Error deserializing JSON to Map", e);
        }
    }

    // 对象转 JSON 字符串并格式化（用于调试）
    public static String toPrettyJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error serializing object to pretty JSON", e);
            throw new IllegalArgumentException("Error serializing object to pretty JSON", e);
        }
    }

}
