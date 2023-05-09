package com.lear.service;

import com.lear.entity.User;
import com.lear.entity.UserFriend;
import com.lear.mapper.UserFriendMapper;
import com.lear.mapper.UserMapper;
import com.lear.util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务类
 * @author 天狗
 */
public class UserService {

    public static boolean register(User user) {
        User u = selectByUsername(user.getUsername());
        if (u!=null) {
            return false;
        }
        user.setPassword(PasswordUtil.generate(user.getPassword()));
        return UserMapper.insert(user);
    }

    public static boolean login(User user) {
        String rawPassword = user.getPassword();
        User loginUser = selectByUsername(user.getUsername());
        if (loginUser==null) {
            return false;
        } else {
            return PasswordUtil.verify(rawPassword, loginUser.getPassword());
        }

    }

    private static User selectByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> userList = UserMapper.selectCustom(sql, username);
        if (0 < userList.size()) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    public static String getNameById(String userId) {
        User user = UserMapper.selectOne(userId);
        if (user==null) {
            return "";
        } else {
            return user.getUsername();
        }
    }

    public static List<User> getFriendByUserId(String userId) {
        List<UserFriend> ufList = UserFriendMapper.selectByUserId(userId);
        ArrayList<User> result = new ArrayList<>();
        for (UserFriend userFriend : ufList) {
            Integer friendId;
            if (userId.equals(userFriend.getUser_1().toString())) {
                friendId = userFriend.getUser_2();
            } else {
                friendId = userFriend.getUser_1();
            }
            User user = UserMapper.selectOne(friendId.toString());
            if (user!=null) {
                result.add(user);
            }
        }
        return result;
    }

    public static boolean addFriend(String userId, String friendId) {
        if (UserFriendMapper.isFriend(userId, friendId)) {
            return false;
        }
        UserFriend userFriend = new UserFriend();
        userFriend.setUser_1(Integer.valueOf(userId));
        userFriend.setUser_2(Integer.valueOf(friendId));
        return UserFriendMapper.insert(userFriend);
    }

}
