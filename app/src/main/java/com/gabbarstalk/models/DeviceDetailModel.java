package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 26/03/2019.
 */

public class DeviceDetailModel implements Serializable {
    @SerializedName("platform")
    private  String platform = "Android";

    @SerializedName("model")
    private  String model;

    @SerializedName("appVersionStri")
    private String appVersion;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "DeviceDetailModel{" +
                "platform='" + platform + '\'' +
                ", model='" + model + '\'' +
                ", appVersion='" + appVersion + '\'' +
                '}';
    }
}
