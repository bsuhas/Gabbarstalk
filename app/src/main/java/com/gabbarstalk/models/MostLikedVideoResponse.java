package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SUHAS on 27/03/2019.
 */

public class MostLikedVideoResponse {
    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("mostLikedAgendaWithVideoModels")
    private List<MostLikedAgendaWithVideoModel> mostLikedAgendaWithVideoModels;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<MostLikedAgendaWithVideoModel> getMostLikedAgendaWithVideoModels() {
        return mostLikedAgendaWithVideoModels;
    }

    public void setMostLikedAgendaWithVideoModels(List<MostLikedAgendaWithVideoModel> mostLikedAgendaWithVideoModels) {
        this.mostLikedAgendaWithVideoModels = mostLikedAgendaWithVideoModels;
    }
}
