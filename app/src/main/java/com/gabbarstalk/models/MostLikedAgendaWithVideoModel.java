package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 27/03/2019.
 */

public class MostLikedAgendaWithVideoModel implements Serializable {
    @SerializedName("agenda_id")
    private int agendaId;

    @SerializedName("agenda_title")
    private String agendaTitle;

    @SerializedName("videoDetailsModel")
    private  VideoDetailsModel videoDetailsModel;

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

    public VideoDetailsModel getVideoDetailsModel() {
        return videoDetailsModel;
    }

    public void setVideoDetailsModel(VideoDetailsModel videoDetailsModel) {
        this.videoDetailsModel = videoDetailsModel;
    }
}
