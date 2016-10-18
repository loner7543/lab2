package com.lab_2;

import java.util.Date;
import java.util.List;

/**
 * Created by Александр on 18.10.2016.
 */

public class Meeting {
    public String Name;
    public String Description;
    public Date FromDate;
    public Date ToDate;
    public List<Participant> Participants;
    public String Type;

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

    public Date getFromDate() {
        return FromDate;
    }

    public void setFromDate(Date fromDate) {
        FromDate = fromDate;
    }

    public Date getToDate() {
        return ToDate;
    }

    public void setToDate(Date toDate) {
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
