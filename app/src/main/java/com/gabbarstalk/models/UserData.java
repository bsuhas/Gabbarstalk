package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 07/03/2017.
 */

public class UserData implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;

    private String userId;

    @SerializedName("deviceDetail")
    @Expose
    private DeviceDetailModel deviceDetail;

    @SerializedName("referralCode")
    @Expose
    private String referralCode = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DeviceDetailModel getDeviceDetail() {
        return deviceDetail;
    }

    public void setDeviceDetail(DeviceDetailModel deviceDetail) {
        this.deviceDetail = deviceDetail;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", userId='" + userId + '\'' +
                ", deviceDetail=" + deviceDetail +
                ", referralCode='" + referralCode + '\'' +
                '}';
    }
}