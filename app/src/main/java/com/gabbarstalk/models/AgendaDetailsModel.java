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

    @Override
    public String toString() {
        return "AgendaDetailsModel{" +
                "agendaId=" + agendaId +
                ", agendaTitle='" + agendaTitle + '\'' +
                ", videoDetailsModelList=" + videoDetailsModelList +
                '}';
    }
}
