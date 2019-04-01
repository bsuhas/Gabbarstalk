package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SUHAS on 27/03/2019.
 */

public class EmptyResponse implements Serializable {
    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("error_msg")
    private String errorMsg;


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


    @Override
    public String toString() {
        return "RecentVideoResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
