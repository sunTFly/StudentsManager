package com.example.management.main.content;

/**
 * Created by Avecle on 2019/3/15.
 */

public class NoticeInfo {

    private int PicID;
    private String noticeName;
    private String noticeText;
    private String noticeDate;

    public int getPicID() {
        return PicID;
    }

    public void setPicID(int picID) {
        PicID = picID;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }
}
