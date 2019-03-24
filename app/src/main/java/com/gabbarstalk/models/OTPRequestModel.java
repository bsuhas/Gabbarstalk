package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 23/03/2019.
 */

public class OTPRequestModel implements Serializable {

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "OTPRequestModel{" +
                "otp='" + otp + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
