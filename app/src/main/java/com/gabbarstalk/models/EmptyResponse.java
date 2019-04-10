package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SUHAS on 27/03/2019.
 */

public class EmptyResponse implements Serializable {
    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("user_profile_img")
    @Expose
    private String profileImgURL;


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

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    @Override
    public String toString() {
        return "EmptyResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", profileImgURL='" + profileImgURL + '\'' +
                '}';
    }
}
