package com.lear.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * api对象转换工具类
 * @author 天狗
 */
public class ApiObjConvertUtil {

    public static <T> T convert(String params, Class<T> cls) {
        String[] paramList = params.split("&");
        try {
            T obj = cls.newInstance();
            for (String param : paramList) {
                String[] kv = param.split("=");
                String key = kv[0];
                String value = kv[1];
                BeanUtils.setProperty(obj, key, value);
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
