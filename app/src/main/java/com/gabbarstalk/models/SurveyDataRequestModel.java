package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 01/04/2017.
 */

public class SurveyDataRequestModel implements Serializable {
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
    @SerializedName("PrimaryKey")
    @Expose
    private String primaryKey;
    @SerializedName("Aadharno")
    @Expose
    private String aadharno;
    @SerializedName("Mobileno")
    @Expose
    private String mobileno;
    @SerializedName("Voterstatus")
    @Expose
    private String voterstatus;

    public String getVoterstatus() {
        return voterstatus;
    }

    public void setVoterstatus(String voterstatus) {
        this.voterstatus = voterstatus;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    @Override
    public String toString() {
        return "SurveyDataRequestModel{" +
                "loksabhaId='" + loksabhaId + '\'' +
                ", vidhansabhaId='" + vidhansabhaId + '\'' +
                ", wardNumber='" + wardNumber + '\'' +
                ", userID='" + userID + '\'' +
                ", primaryKey='" + primaryKey + '\'' +
                ", aadharno='" + aadharno + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", voterstatus='" + voterstatus + '\'' +
                '}';
    }
}