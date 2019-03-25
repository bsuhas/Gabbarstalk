package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 25/03/2019.
 */

public class VideoDetailsModel implements Serializable{

    @SerializedName("video_url")
    private String videoUrl;

    @SerializedName("video_thumbnail")
    private String videoThumbnail;

    @SerializedName("like_count")
    private int likeCount = 5;

    @SerializedName("share_url")
    private String shareUrl = "Test";

    @SerializedName("user_img")
    private String userImg = "test";

    @SerializedName("user_name")
    private String userName;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "VideoDetailsModel{" +
                "videoUrl='" + videoUrl + '\'' +
                ", videoThumbnail='" + videoThumbnail + '\'' +
                ", likeCount=" + likeCount +
                ", shareUrl='" + shareUrl + '\'' +
                ", userImg='" + userImg + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
