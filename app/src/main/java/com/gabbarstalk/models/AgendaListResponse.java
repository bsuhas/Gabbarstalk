package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SUHAS on 24/03/2019.
 */

public class AgendaListResponse implements Serializable {

    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("agendaDetailList")
    private List<AgendaDetailsModel> agendaDetailList;

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

    public List<AgendaDetailsModel> getAgendaDetailList() {
        return agendaDetailList;
    }

    public void setAgendaDetailList(List<AgendaDetailsModel> agendaDetailList) {
        this.agendaDetailList = agendaDetailList;
    }

    @Override
    public String toString() {
        return "AgendaListResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", agendaDetailList=" + agendaDetailList +
                '}';
    }
}
