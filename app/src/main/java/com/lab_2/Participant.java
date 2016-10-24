package com.lab_2;

/**
 * Created by Александр on 18.10.2016.
 */

public class Participant {
    private String Fio;
    private String Position;

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
}
