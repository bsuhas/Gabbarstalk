package com.gabbarstalk.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 24/03/2019.
 */

public class AgendaDetailsModel implements Serializable{
    @SerializedName("agenda_id")
    private int agendaId;

    @SerializedName("agenda_title")
    private String agendaTitle;

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
                '}';
    }
}
