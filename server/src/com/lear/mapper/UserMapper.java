package com.lear.mapper;

import com.lear.entity.User;
import com.lear.util.JdbcUtil;

import java.util.List;

/**
 * 用户数据访问层
 * @author 天狗
 * @date 2023/04/29
 */
public class UserMapper {

    public static boolean insert(User user) {
        String sql = "insert into user (username, password) values (?, ?)";
        return JdbcUtil.executeDML(sql, user.getUsername(), user.getPassword());
    }

    public static boolean update(User user) {
        String sql = "update user set username = ?, password = ? where id = ?";
        return JdbcUtil.executeDML(sql, user.getUsername(), user.getPassword(), user.getId());
    }

    public static boolean delete(String id) {
        String sql = "delete from user where id = ?";
        return JdbcUtil.executeDML(sql, id);
    }

    public static User selectOne(String id) {
        String sql = "select * from user where id = ?";
        List<User> userList = JdbcUtil.executeDQL(User.class, sql, id);
        if (userList.size() == 0) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    public static List<User> selectAll() {
        String sql = "select * from user";
        return JdbcUtil.executeDQL(User.class, sql);
    }

    public static List<User> selectCustom(String sql, Object... params) {
        return JdbcUtil.executeDQL(User.class, sql, params);
    }

    public static User getByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> userList = JdbcUtil.executeDQL(User.class, sql, username);
        if (userList.size() == 0) {
            return null;
        } else {
            return userList.get(0);
        }
    }


}
