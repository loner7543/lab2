package com.lab_2.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Александр on 18.10.2016.
 */

public class Participant implements Serializable {
    private String Fio;
    private String Position;

    public Participant(){

    }

    public String getFio() {
        return Fio;
    }

    public Participant(String fio, String position) {
        Fio = fio;
        Position = position;
    }

    public void setFio(String fio) {
        Fio = fio;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public Map<String,String> toMap(){
        HashMap<String,String> rs = new HashMap<>();
        rs.put("Fio",Fio);
        rs.put("Position",Position);
        return  rs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FIO  ").append(Fio);
        sb.append("\n");

        sb.append("Position  ").append(Position).append("\n");
        return sb.toString();
    }
}
