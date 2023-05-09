package com.lear.request;

import com.lear.entity.api.UserRequestModel;
import com.lear.util.StringUtils;

/**
 * 用户请求类
 * 包括用户登录注册等接口请求
 * @author 天狗
 */
public class UserRequest {

    public static String login(UserRequestModel req) {
        return BaseRequest.requestServer("/user/login", "username=" + req.getUsername(), "password=" + req.getPassword());
    }

    public static String register(UserRequestModel req) {
        return BaseRequest.requestServer("/user/register", "username=" + req.getUsername(), "password=" + req.getPassword());
    }

}
