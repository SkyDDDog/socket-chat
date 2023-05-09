package com.lear.util;


/**
 * 请求参数转实体类工具类
 * @author 天狗
 */
public class RequestModelUtil {

    /**
     * 从请求路径中提取参数并转换为实体类
     * @param cls   实体类的class对象
     * @param path  请求路径
     * @return
     */
    public static <T> T toModel(Class<T> cls, String path) {
        String params = path.replaceAll(".*\\?", "");
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        String[] paramArr = params.split("&");

        try {
            T obj = cls.newInstance();
            for (String s : paramArr) {
                String[] kv = s.split("=");
                String key = kv[0];
                String value = kv[1];
                // 获取属性
                java.lang.reflect.Field field = cls.getDeclaredField(key);
                // 设置属性可访问
                field.setAccessible(true);
                // 设置属性值
                field.set(obj, value);
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }

    }


}
