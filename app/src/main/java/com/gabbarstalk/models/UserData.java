package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 07/03/2017.
 */

public class UserData {

    @SerializedName("LoksabhaId")
    @Expose
    private String loksabhaId;
    @SerializedName("VidhansabhaId")
    @Expose
    private String vidhansabhaId;
    @SerializedName("WardNumber")
    @Expose
    private String wardNumber;
    @SerializedName("IMEI")
    @Expose
    private String iMEI;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;

    private  String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoksabhaId() {
        return loksabhaId;
    }

    public void setLoksabhaId(String loksabhaId) {
        this.loksabhaId = loksabhaId;
    }

    public String getVidhansabhaId() {
        return vidhansabhaId;
    }

    public void setVidhansabhaId(String vidhansabhaId) {
        this.vidhansabhaId = vidhansabhaId;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getiMEI() {
        return iMEI;
    }

    public void setiMEI(String iMEI) {
        this.iMEI = iMEI;
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

    @Override
    public String toString() {
        return "UserData{" +
                "loksabhaId='" + loksabhaId + '\'' +
                ", vidhansabhaId='" + vidhansabhaId + '\'' +
                ", wardNumber='" + wardNumber + '\'' +
                ", iMEI='" + iMEI + '\'' +
                ", username='" + username + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}