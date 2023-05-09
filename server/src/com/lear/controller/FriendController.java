package com.lear.controller;

import com.lear.entity.User;
import com.lear.entity.api.FriendRequestModel;
import com.lear.service.UserService;
import com.lear.util.ApiObjConvertUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 好友相关接口
 * @author 天狗
 */
public class FriendController {

    public void addFriend(String params, OutputStream outputStream) {
        FriendRequestModel req = ApiObjConvertUtil.convert(params, FriendRequestModel.class);
        String result = "";
        if (req == null) {
            result = "false";
        } else {
            if (UserService.addFriend(req.getUserId(), req.getFriendId())) {
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

    public void friendList(String params, OutputStream outputStream) {
        FriendRequestModel req = ApiObjConvertUtil.convert(params, FriendRequestModel.class);
        String result = "";
        if (req == null) {
            result = "false";
        } else {
            List<User> friendList = UserService.getFriendByUserId(req.getUserId());
            result = this.buildFriendList(friendList);
        }

        try {
            outputStream.write(result.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildFriend(User user) {
        return user.getUsername() +
                '(' +
                user.getId() +
                ')';
    }

    private String buildFriendList(List<User> userList) {
        StringBuilder builder = new StringBuilder();
        for (User user : userList) {
            builder.append(this.buildFriend(user))
                    .append(';');
        }
        return builder.toString();
    }

}
