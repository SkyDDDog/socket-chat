package com.lear.request;

/**
 * 好友请求类
 * @author 天狗
 */
public class FriendRequest {


    public static String addFriend(String userId, String friendId) {
        return BaseRequest.requestServer("/friend/add", "userId=" + userId, "friendId=" + friendId);
    }

    public static String friendList(String userId) {
        return BaseRequest.requestServer("/friend/list", "userId=" + userId);
    }


}
