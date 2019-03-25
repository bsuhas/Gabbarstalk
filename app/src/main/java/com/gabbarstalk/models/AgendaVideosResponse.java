package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SUHAS on 25/03/2019.
 */

public class AgendaVideosResponse implements Serializable {

    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("videoList")
    private List<VideoDetailsModel> videoDetailsModelList;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<VideoDetailsModel> getVideoDetailsModelList() {
        return videoDetailsModelList;
    }

    public void setVideoDetailsModelList(List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = videoDetailsModelList;
    }

    @Override
    public String toString() {
        return "AgendaVideosResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", videoDetailsModelList=" + videoDetailsModelList +
                '}';
    }
}
