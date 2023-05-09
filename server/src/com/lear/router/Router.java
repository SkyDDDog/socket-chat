package com.lear.router;

import com.lear.Application;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 简单的路由处理
 * @author 天狗
 */
public class Router {

    private static final HashMap<String, RouterInfo>  routerMap = new HashMap<>();

    private static final String basePackage = "com.lear.controller.";
    private static final Logger logger = Logger.getLogger(Router.class.getName());

    // 初始化路由
    static {
        routerMap.put("/user/login", new RouterInfo(basePackage+"UserController", "login"));
        routerMap.put("/user/register", new RouterInfo(basePackage+"UserController", "register"));
        routerMap.put("/friend/add", new RouterInfo(basePackage+"FriendController", "addFriend"));
        routerMap.put("/friend/list", new RouterInfo(basePackage+"FriendController", "friendList"));
    }

    private static String handlePath(String rawPath) {
        if (rawPath == null) {
            return null;
        }
        return rawPath.split("\\?")[0];
    }

    private static String getParams(String rawPath) {
        if (rawPath == null) {
            return null;
        }
        return rawPath.split("\\?")[1];
    }

    /**
     * 执行路由对应方法
     */
    public static void invoke(String path, OutputStream outputStream) {
        String params = getParams(path);
        path = handlePath(path);
        RouterInfo routerInfo = routerMap.get(path);
        logger.info("path: "+path);
        if (routerInfo == null) {
            logger.info("路由不存在");
            return;
        }
        try {
            Class<?> clszz = Class.forName(routerInfo.getClassName());
            Object obj = clszz.newInstance();
            Method method = clszz.getMethod(routerInfo.getMethodName(), String.class, OutputStream.class);
            method.invoke(obj, params, outputStream);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            logger.info("路由处理类不存在");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
