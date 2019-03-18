package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 01/04/2017.
 */

public class SurveyStatusModel {
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }
}
