package com.tamaris.SafetyNet.service;

import com.tamaris.SafetyNet.model.Medicalrecord;
import com.tamaris.SafetyNet.repository.MedicalrecordRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalrecordService {

    private final MedicalrecordRepository medicalrecordRepository;

    public MedicalrecordService(MedicalrecordRepository medicalrecordRepository) {
        this.medicalrecordRepository = medicalrecordRepository;
    }

    //ajouter un dossier médical
    public void addMedicalrecord(Medicalrecord medicalrecord) {
        medicalrecordRepository.addMedicalrecord(medicalrecord);
    }

    //mettre à jour un dossier médical existant
    public void updateMedicalrecord(Medicalrecord medicalrecord) {
        medicalrecordRepository.updateMedicalrecord(medicalrecord);
    }

    //supprimer un dossier médical
    public void deleteMedicalrecord(String firstName, String lastName) {
        medicalrecordRepository.deleteMedicalrecord(firstName, lastName);
    }
}
