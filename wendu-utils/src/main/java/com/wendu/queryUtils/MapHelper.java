package com.wendu.queryUtils;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LittleDumpling
 */
public class MapHelper {
    public static Map<String,Object> queryCondition(Object param){

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if(param instanceof Map){
                map.putAll((Map<? extends String, ?>) param);
            }else{
                map.putAll(beanToMap(param));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            org.springframework.cglib.beans.BeanMap beanMap= org.springframework.cglib.beans.BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }


}
