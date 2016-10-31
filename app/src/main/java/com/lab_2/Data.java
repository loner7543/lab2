package com.lab_2;

/**
 * Created by Александр on 31.10.2016.
 */

public class Data {
    private String Description;
    private String FromDate;
    private int Name;
    private String ToDate;
    private String Type;

  public Data(){

  }

    public Data(String description, String fromDate, int name, String toDate, String type) {
        Description = description;
        FromDate = fromDate;
        Name = name;
        ToDate = toDate;
        Type = type;
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

    public int getName() {
        return Name;
    }

    public void setName(int name) {
        Name = name;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
