package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 25/03/2019.
 */

public class VideoDetailsModel implements Serializable {

    @SerializedName("video_id")
    private String videoId;

    @SerializedName("video_url")
    private String videoUrl;

    @SerializedName("video_title")
    private String caption;

    @SerializedName("video_thumb")
    private String videoThumbnail;

    @SerializedName("likes")
    private int likeCount;

    @SerializedName("share_url")
    private String shareUrl;

    @SerializedName("user_img")
    private String userImg;

    @SerializedName("name")
    private String profileName;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("agenda_id")
    private String agendaId;

    @SerializedName("agenda_title")
    private String agendaTitle;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public String getAgendaTitle() {
        return agendaTitle;
    }

    public void setAgendaTitle(String agendaTitle) {
        this.agendaTitle = agendaTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "VideoDetailsModel{" +
                "videoId='" + videoId + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", caption='" + caption + '\'' +
                ", videoThumbnail='" + videoThumbnail + '\'' +
                ", likeCount=" + likeCount +
                ", shareUrl='" + shareUrl + '\'' +
                ", userImg='" + userImg + '\'' +
                ", profileName='" + profileName + '\'' +
                ", userName='" + userName + '\'' +
                ", agendaId='" + agendaId + '\'' +
                ", agendaTitle='" + agendaTitle + '\'' +
                '}';
    }
}
