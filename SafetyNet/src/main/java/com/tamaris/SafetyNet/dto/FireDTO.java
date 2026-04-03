package com.tamaris.SafetyNet.dto;

import java.util.List;

public class FireDTO {

    private String station;
    private List<FloodDTO> persons;


    public FireDTO() {
    }

    public FireDTO(String station, List<FloodDTO> persons) {
        this.station = station;
        this.persons = persons;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<FloodDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<FloodDTO> persons) {
        this.persons = persons;
    }
}
