package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SUHAS on 10/03/2017.
 */

public class RegisterResponseModel {
    @SerializedName("Status")
    @Expose
    private List<StatusModel> status = null;

    public List<StatusModel> getStatus() {
        return status;
    }

    public void setStatus(List<StatusModel> status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RegisterResponseModel{" +
                "status=" + status +
                '}';
    }
}
