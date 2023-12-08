package com.sondev.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class commonUtils {
    public static Map<String, Object> convertObjectToMap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(object);
            map.put(fieldName, fieldValue);
        }

        return map;
    }
}
