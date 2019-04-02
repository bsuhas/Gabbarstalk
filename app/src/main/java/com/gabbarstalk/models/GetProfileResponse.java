package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SUHAS on 02/04/2019.
 */

public class GetProfileResponse implements Serializable {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private List<ProfileData> profileDataList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProfileData> getProfileDataList() {
        return profileDataList;
    }

    public void setProfileDataList(List<ProfileData> profileDataList) {
        this.profileDataList = profileDataList;
    }

    @Override
    public String toString() {
        return "GetProfileResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", profileDataList=" + profileDataList +
                '}';
    }
}
