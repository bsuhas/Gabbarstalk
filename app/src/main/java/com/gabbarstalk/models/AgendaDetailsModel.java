package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SUHAS on 24/03/2019.
 */

public class AgendaDetailsModel implements Serializable {
    @SerializedName("agenda_id")
    private int agendaId;

    @SerializedName("agenda_title")
    private String agendaTitle;

    @SerializedName("agenda_content")
    private String agendaDetails;

    @SerializedName("videoCounts")
    private String totalVideoCount;

    @SerializedName("videos")
    private List<VideoDetailsModel> videoDetailsModelList;

    public List<VideoDetailsModel> getVideoDetailsModelList() {
        return videoDetailsModelList;
    }

    public void setVideoDetailsModelList(List<VideoDetailsModel> videoDetailsModelList) {
        this.videoDetailsModelList = videoDetailsModelList;
    }

    public int getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(int agendaId) {
        this.agendaId = agendaId;
    }

    public String getAgendaTitle() {
        return agendaTitle;
    }

    public void setAgendaTitle(String agendaTitle) {
        this.agendaTitle = agendaTitle;
    }

    public String getTotalVideoCount() {
        return totalVideoCount;
    }

    public void setTotalVideoCount(String totalVideoCount) {
        this.totalVideoCount = totalVideoCount;
    }

    public String getAgendaDetails() {
        return agendaDetails;
    }

    public void setAgendaDetails(String agendaDetails) {
        this.agendaDetails = agendaDetails;
    }

    @Override
    public String toString() {
        return "AgendaDetailsModel{" +
                "agendaId=" + agendaId +
                ", agendaTitle='" + agendaTitle + '\'' +
                ", agendaDetails='" + agendaDetails + '\'' +
                ", totalVideoCount='" + totalVideoCount + '\'' +
                ", videoDetailsModelList=" + videoDetailsModelList +
                '}';
    }
}
