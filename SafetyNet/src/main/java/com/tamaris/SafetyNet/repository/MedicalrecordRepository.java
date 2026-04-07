package com.tamaris.SafetyNet.repository;

import com.tamaris.SafetyNet.model.Firestation;
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

    //ajouter un dossier médical
    public void addMedicalrecord(Medicalrecord medicalrecord) {
        dataHendler.getData().getMedicalrecords().add(medicalrecord);
    }

    //mettre à jour un dossier médical existant
    public void updateMedicalrecord(Medicalrecord medicalrecord) {
        List<Medicalrecord> medicalrecords = dataHendler.getData().getMedicalrecords();
        for(int mr = 0; mr < medicalrecords.size(); mr++) {
            if(medicalrecords.get(mr).getFirstName().equals(medicalrecord.getFirstName())
            && medicalrecords.get(mr).getLastName().equals(medicalrecord.getLastName())) {
                medicalrecords.set(mr, medicalrecord);
                break;
            }
        }
    }

    //supprimer un dossier médical
    public void deleteMedicalrecord(String firstName, String lastName) {
        dataHendler.getData().getMedicalrecords()
                .removeIf(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName));
    }

}
