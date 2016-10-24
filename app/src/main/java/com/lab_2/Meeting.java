package com.lab_2;

import java.util.Date;
import java.util.List;

/**
 * Created by Александр on 18.10.2016.
 */

public class Meeting {
    private String Name;
    private String Description;
    private String FromDate;
    private String ToDate;
    private List<Participant> Participants;
    private String Type;

    public Meeting(String name, String description, String fromDate, String toDate, List<Participant> participants, String type) {
        Name = name;
        Description = description;
        FromDate = fromDate;
        ToDate = toDate;
        Participants = participants;
        Type = type;
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
}
