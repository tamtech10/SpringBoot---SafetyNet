package com.tamaris.SafetyNet.repository;

import com.tamaris.SafetyNet.model.Medicalrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalrecordRepository {


    private final DataHendler dataHendler;

    public MedicalrecordRepository(DataHendler dataHendler) {
        this.dataHendler = dataHendler;
    }

    //list of all medicalrecords
    public List<Medicalrecord> findAllMedicalrecords() {
        return dataHendler.getData().getMedicalrecords();
    }

    //da pristupimo imenu i prezimenu
    public Medicalrecord findByNameLastname(String firstName, String lastName) {
        for (Medicalrecord record : findAllMedicalrecords()) {
            if (record.getFirstName().equals(firstName) && record.getLastName().equals(lastName)) {
                return record;
            }
        } return null;
    }

}
