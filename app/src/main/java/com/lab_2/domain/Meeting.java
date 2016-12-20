package com.lab_2.domain;

import com.lab_2.domain.Participant;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Александр on 18.10.2016.
 */

public class Meeting implements Serializable {
    private String Key;
    private String Name;
    private String Description;
    private String FromDate;
    private String ToDate;
    private String Type;
    private List<Participant> Participants;
    private boolean isGoing;//показывает будет пользователь учавствовать или нет

    public Meeting(){

    }

    public Meeting(String name, String description, String fromDate, String toDate, List<Participant> participants, String type) {
        Name = name;
        Description = description;
        FromDate = fromDate;
        ToDate = toDate;
        Type = type;
        Participants = participants;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public List<Participant> getParticipants() {
        return Participants;
    }

    public void setParticipants(List<Participant> participants) {
        Participants = participants;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public void setGoing(boolean going) {
        isGoing = going;
    }

   /* @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Key).append("\n").append("Meeting name  ").append(Name).append("\n");
        sb.append("Description  ").append(Description).append("\n");
        sb.append("From date  ").append(FromDate).append("\n");
        sb.append("To date  ").append(ToDate).append("\n");
        sb.append("Type  ").append(Type).append("\n");
        sb.append("Participants").append("\n");
        for (Participant participant:Participants){
            sb.append(participant.toString());
        }
        return sb.toString();
    }*/
}
