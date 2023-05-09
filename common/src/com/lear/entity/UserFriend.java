package com.lear.entity;

/**
 * 用户-好友表 数据库实体类
 * @author 天狗
 */
public class UserFriend {

    private Integer id;
    private Integer user_1;
    private Integer user_2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_1() {
        return user_1;
    }

    public void setUser_1(Integer user_1) {
        this.user_1 = user_1;
    }

    public Integer getUser_2() {
        return user_2;
    }

    public void setUser_2(Integer user_2) {
        this.user_2 = user_2;
    }

    @Override
    public String toString() {
        return "UserFriend{" +
                "id=" + id +
                ", user_1=" + user_1 +
                ", user_2=" + user_2 +
                '}';
    }
}
