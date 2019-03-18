package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SUHAS on 11/03/2017.
 */

public class VoterDataResponseModel {
    @SerializedName("Status")
    @Expose
    private List<StatusModel> status = null;
    @SerializedName("VoterData")
    @Expose
    private List<VoterDetailModel> voterData = null;

    public List<StatusModel> getStatus() {
        return status;
    }

    public void setStatus(List<StatusModel> status) {
        this.status = status;
    }

    public List<VoterDetailModel> getVoterData() {
        return voterData;
    }

    public void setVoterData(List<VoterDetailModel> voterData) {
        this.voterData = voterData;
    }

    @Override
    public String toString() {
        return "VoterDataResponseModel{" +
                "status=" + status +
                ", voterData=" + voterData +
                '}';
    }
}
