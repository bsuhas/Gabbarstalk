package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 11/03/2017.
 */

public class VoterDataRequestModel {

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
    @SerializedName("StartID")
    @Expose
    private String startID;

    @SerializedName("EndID")
    @Expose
    private String endID;

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

    public String getStartID() {
        return startID;
    }

    public void setStartID(String startID) {
        this.startID = startID;
    }

    public String getEndID() {
        return endID;
    }

    public void setEndID(String endID) {
        this.endID = endID;
    }

    @Override
    public String toString() {
        return "VoterDataRequestModel{" +
                "loksabhaId='" + loksabhaId + '\'' +
                ", vidhansabhaId='" + vidhansabhaId + '\'' +
                ", wardNumber='" + wardNumber + '\'' +
                ", userID='" + userID + '\'' +
                ", startID='" + startID + '\'' +
                ", endID='" + endID + '\'' +
                '}';
    }
}
