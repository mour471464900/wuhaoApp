package com.anfeng.wuhao.anfengkuaikan.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * ============================
 * 作者： 吴浩
 * 时间： 2017/6/28.
 * 描述：
 * =============================
 */

public class UserInfo extends DataSupport implements Serializable {
     private String username;  // 用户名
     private String token;     // 服务器返回的唯一token标识
     private String avatar;    // 头像
     private int  fee     ;    // 账号余额

    public  UserInfo(){
        this(null,null);
    }

    public UserInfo(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", avatar='" + avatar + '\'' +
                ", fee=" + fee +
                '}';
    }
}
