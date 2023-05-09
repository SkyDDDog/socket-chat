package com.lear.controller;

import com.lear.entity.User;
import com.lear.entity.api.UserRequestModel;
import com.lear.mapper.UserMapper;
import com.lear.service.UserService;
import com.lear.util.ApiObjConvertUtil;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 用户处理器
 * @author 天狗
 */
public class UserController {

    public void register(String params, OutputStream outputStream) {
        UserRequestModel req = ApiObjConvertUtil.convert(params, UserRequestModel.class);
        String result = "";
        if (req == null) {
            result = "false";
        } else {
            User user = new User();
            user.setUsername(req.getUsername());
            user.setPassword(req.getPassword());
            if (UserService.register(user)) {
                result = "success";
            } else {
                result = "false";
            }
        }

        try {
            outputStream.write(result.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void login(String params, OutputStream outputStream) {
        UserRequestModel req = ApiObjConvertUtil.convert(params, UserRequestModel.class);
        String result = "";
        if (req == null) {
            result = "false";
        } else {
            User user = new User();
            user.setUsername(req.getUsername());
            user.setPassword(req.getPassword());
            if (UserService.login(user)) {
                User newUser = UserMapper.getByUsername(req.getUsername());
                if (newUser!=null) {
                    result = "true,"+newUser.getId();
                } else {
                    result = "false";
                }
            } else {
                result = "false";
            }
        }

        try {
            outputStream.write(result.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
