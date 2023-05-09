package com.lear.util;

/**
 * 字符串工具类
 * @author 天狗
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

}
