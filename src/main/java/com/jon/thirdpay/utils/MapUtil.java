/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.jon.thirdpay.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.simpleframework.xml.Element;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by null on 2017/2/14.
 */
public class MapUtil {

    final static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, String> buildMap(Object obj) {
        Map<String, String> map = new HashMap<>();

        try {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();

                //如果 element 注解 name 字段设置了内容, 使用其当成字段名
                Element element = field.getAnnotation(Element.class);
                if (element != null && !StringUtils.isEmpty(element.name())) {
                    fieldName = element.name();
                }

                String value = field.get(obj) == null ? "" : String.valueOf(field.get(obj));
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 表单字符串转化成 hashMap
     * @param orderinfo
     * @return
     */
    public static HashMap<String, String> form2Map( String orderinfo) {
        String listinfo[];
        HashMap<String, String> map = new HashMap<String, String>();
        listinfo = orderinfo.split("&");
        for(String s : listinfo)
        {
            String list[]  = s.split("=");
            if(list.length>1)
            {
                map.put(list[0], list[1]);
            }
        }
        return map;
    }

    /**
     * 表单字符串转化成 hashMap，将具有下划线的key转换为小驼峰
     * @param orderinfo,
     * @return
     */
    public static HashMap<String, String> form2MapWithCamelCase( String orderinfo) {
        String listinfo[];
        HashMap<String, String> map = new HashMap<String, String>();
        listinfo = orderinfo.split("&");
        for(String s : listinfo)
        {
            String list[]  = s.split("=");
            if(list.length>1)
            {
                map.put(CamelCaseUtil.toCamelCase(list[0]),list[1]);
            }
        }
        return map;
    }

    public static <T> T mapToObject(Object obj,Class<T> clazz) {
        try {
            return objectMapper.readValue(serialize(obj),clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
