package com.example.management.main.chat;

import android.graphics.Bitmap;

public class Msg {
    public static final int TYPE_RECEIVED = 0;// 接收消息
    public static final int TYPE_SENT = 1;// 发送消息

    private String content;
    private Bitmap me_pic;
    private int friends_pic;

    public int getFriends_pic() {
        return friends_pic;
    }

    public void setFriends_pic(int friends_pic) {
        this.friends_pic = friends_pic;
    }

    public Bitmap getMe_pic() {
        return me_pic;
    }

    public void setMe_pic(Bitmap me_pic) {
        this.me_pic = me_pic;
    }

    private int type;

    public Msg(String content, int type ,Bitmap me_pic , int friends_pic) {
        this.content = content;
        this.type = type;
        this.me_pic = me_pic;
        this.friends_pic = friends_pic;
    }

    public static int getTypeReceived() {
        return TYPE_RECEIVED;
    }

    public static int getTypeSent() {
        return TYPE_SENT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}