package com.example.management.menu.night_records;

/**
 * Created by Avecle on 2019/4/4.
 */

public class NightRecordsInfo {
    private String sName;
    private String room;
    private int state;
    private String date;


    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
