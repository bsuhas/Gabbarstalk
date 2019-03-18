package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUHAS on 10/03/2017.
 */

public class StatusModel {
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("errorcode")
    @Expose
    private Integer errorcode;
    @SerializedName("errormsg")
    @Expose
    private String errormsg;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    @Override
    public String toString() {
        return "StatusModel{" +
                "userid='" + userid + '\'' +
                ", active='" + active + '\'' +
                ", errorcode=" + errorcode +
                ", errormsg='" + errormsg + '\'' +
                '}';
    }
}
