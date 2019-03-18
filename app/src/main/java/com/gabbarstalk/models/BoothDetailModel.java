package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 11/03/2017.
 */

public class BoothDetailModel {
    @SerializedName("BID")
    @Expose
    private Integer bID;
    @SerializedName("BoothAddress")
    @Expose
    private String boothAddress;

    public Integer getBID() {
        return bID;
    }

    public void setBID(Integer bID) {
        this.bID = bID;
    }

    public String getBoothAddress() {
        return boothAddress;
    }

    public void setBoothAddress(String boothAddress) {
        this.boothAddress = boothAddress;
    }

    @Override
    public String toString() {
        return "BoothDetailModel{" +
                "bID=" + bID +
                ", boothAddress='" + boothAddress + '\'' +
                '}';
    }
}
