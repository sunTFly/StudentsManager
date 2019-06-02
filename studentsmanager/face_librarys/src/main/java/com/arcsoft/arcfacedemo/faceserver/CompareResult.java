package com.arcsoft.arcfacedemo.faceserver;



public class CompareResult {
    private String userName;
    private float similar;
    private int trackId;

    public CompareResult(String userName, float similar) {
        this.userName = userName;
        this.similar = similar;
    }


    public String getUserName() {
        return userName;
    }

    public float getSimilar() {
        return similar;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }
}
