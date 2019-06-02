package com.arcsoft.arcfacedemo.model;

import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.LivenessInfo;

public class FacePreviewInfo {
    private FaceInfo faceInfo;
    private LivenessInfo livenessInfo;
    private int trackId;

    public FacePreviewInfo(FaceInfo faceInfo, LivenessInfo livenessInfo, int trackId) {
        this.faceInfo = faceInfo;
        this.livenessInfo = livenessInfo;
        this.trackId = trackId;
    }

    public FaceInfo getFaceInfo() {
        return faceInfo;
    }

    public LivenessInfo getLivenessInfo() {
        return livenessInfo;
    }

    public int getTrackId() {
        return trackId;
    }

}
