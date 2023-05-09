package com.lear.entity.api;

/**
 * 用户好友请求实体类
 * @author 天狗
 */
public class FriendRequestModel {

    private String userId;
    private String friendId;

    public FriendRequestModel() {
    }

    public FriendRequestModel(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "FriendRequestModel{" +
                "userId='" + userId + '\'' +
                ", friendId='" + friendId + '\'' +
                '}';
    }
}
