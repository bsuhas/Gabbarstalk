package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 10/03/2017.
 */

public class RegisterResponseModel {
    @SerializedName("error_code")
    @Expose
    private int errorCode;

    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    @SerializedName("isNewUser")
    @Expose
    private boolean isNewUser;

    @SerializedName("user_id")
    @Expose
    private String userId;

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

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterResponseModel{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", isNewUser=" + isNewUser +
                ", userId='" + userId + '\'' +
                '}';
    }
}
