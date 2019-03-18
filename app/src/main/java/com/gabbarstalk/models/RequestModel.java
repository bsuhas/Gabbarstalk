package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 11/03/2017.
 */

public class RequestModel {

    @SerializedName("LoksabhaId")
    @Expose
    private String loksabhaId;
    @SerializedName("VidhansabhaId")
    @Expose
    private String vidhansabhaId;
    @SerializedName("WardNumber")
    @Expose
    private String wardNumber;
    @SerializedName("UserID")
    @Expose
    private String userID;

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "loksabhaId='" + loksabhaId + '\'' +
                ", vidhansabhaId='" + vidhansabhaId + '\'' +
                ", wardNumber='" + wardNumber + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
