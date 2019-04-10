package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 07/03/2017.
 */

public class ProfileData implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("user_name")
    @Expose
    private String username;

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("mobile_no")
    @Expose
    private String mobileNumber;

    @SerializedName("email_id")
    @Expose
    private String emailId;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("user_profile_img")
    @Expose
    private String profileImgURL;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    @Override
    public String toString() {
        return "ProfileData{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", address='" + address + '\'' +
                ", profileImgURL='" + profileImgURL + '\'' +
                '}';
    }
}