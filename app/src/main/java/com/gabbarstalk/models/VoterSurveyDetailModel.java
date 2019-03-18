package com.gabbarstalk.models;

/**
 * Created by SUHAS on 09/03/2017.
 */

public class VoterSurveyDetailModel {
    private String uniquekey;
    private String aadharNo;
    private String mobileNo;
    private String status;

    public boolean isValid() {
        return uniquekey != null && aadharNo != null && mobileNo != null && status != null;
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VoterSurveyDetailModel{" +
                "uniquekey='" + uniquekey + '\'' +
                ", aadharNo='" + aadharNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
