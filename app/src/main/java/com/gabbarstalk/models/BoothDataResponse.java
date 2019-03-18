package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SUHAS on 11/03/2017.
 */

public class BoothDataResponse {
    @SerializedName("Status")
    @Expose
    private List<StatusModel> status = null;
    @SerializedName("BoothAddress")
    @Expose
    private List<BoothDetailModel> boothModelList = null;

    public List<StatusModel> getStatus() {
        return status;
    }

    public void setStatus(List<StatusModel> status) {
        this.status = status;
    }

    public List<BoothDetailModel> getBoothModelList() {
        return boothModelList;
    }

    public void setBoothModelList(List<BoothDetailModel> boothModelList) {
        this.boothModelList = boothModelList;
    }

    @Override
    public String toString() {
        return "BoothDataResponse{" +
                "status=" + status +
                ", boothModelList=" + boothModelList +
                '}';
    }
}
