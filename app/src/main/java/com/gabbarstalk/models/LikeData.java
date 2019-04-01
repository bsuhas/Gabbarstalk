package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 07/03/2017.
 */


public class LikeData implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("video_id")
    @Expose
    private String videoId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "LikeData{" +
                "userId='" + userId + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }
}