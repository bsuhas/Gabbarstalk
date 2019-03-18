package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SUHAS on 01/04/2017.
 */

public class SurveyDataResponse {

    @SerializedName("Status")
    @Expose
    private List<SurveyStatusModel> status = null;

    public List<SurveyStatusModel> getStatus() {
        return status;
    }

    public void setStatus(List<SurveyStatusModel> status) {
        this.status = status;
    }
}
