package com.lear.mapper;

import com.lear.entity.User;
import com.lear.entity.UserFriend;
import com.lear.util.JdbcUtil;

import java.util.List;
import java.util.stream.Stream;

/**
 * 用户-好友表数据访问层
 * @author 天狗
 */
public class UserFriendMapper {

    public static boolean insert(UserFriend userFriend) {
        Integer user1 = userFriend.getUser_1();
        Integer user2 = userFriend.getUser_2();
        if (user1> user2) {
            int tmp = user2;
            user2 = user1;
            user1 = tmp;
        }
        String sql = "insert into user_friend (user_1, user_2) values (?, ?)";
        return JdbcUtil.executeDML(sql, user1, user2);
    }

    public static boolean update(UserFriend userFriend) {
        Integer user1 = userFriend.getUser_1();
        Integer user2 = userFriend.getUser_2();
        if (user1> user2) {
            int tmp = user2;
            user2 = user1;
            user1 = tmp;
        }
        String sql = "update user_friend set user_1 = ?, user_2 = ? where id = ?";
        return JdbcUtil.executeDML(sql, user1, user2, userFriend.getId());
    }

    public static boolean delete(String id) {
        String sql = "delete from user where id = ?";
        return JdbcUtil.executeDML(sql, id);
    }

    public static UserFriend selectOne(String id) {
        String sql = "select * from user_friend where id = ?";
        List<UserFriend> userList = JdbcUtil.executeDQL(UserFriend.class, sql, id);
        if (userList.size() == 0) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    public static List<UserFriend> selectAll() {
        String sql = "select * from user_friend";
        return JdbcUtil.executeDQL(UserFriend.class, sql);
    }

    public static List<UserFriend> selectCustom(String sql, Object... params) {
        return JdbcUtil.executeDQL(UserFriend.class, sql, params);
    }

    public static List<UserFriend> selectByUserId(String userId) {
        String sql = "select * from user_friend where user_1 = ? or user_2 = ?";
        return JdbcUtil.executeDQL(UserFriend.class, sql, userId, userId);
    }

    public static boolean isFriend(String userId, String friendId) {
        String sql = "select * from user_friend where user_1 = ? AND user_2 = ?";
        return JdbcUtil.executeDQL(UserFriend.class, sql, userId, friendId).size()>0 ||
                JdbcUtil.executeDQL(UserFriend.class, sql, friendId, userId).size()>0;
    }

}
