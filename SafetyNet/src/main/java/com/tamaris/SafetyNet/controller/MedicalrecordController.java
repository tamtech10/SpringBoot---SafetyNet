package com.tamaris.SafetyNet.controller;

import com.tamaris.SafetyNet.model.Medicalrecord;
import com.tamaris.SafetyNet.service.MedicalrecordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MedicalrecordController {

    private final MedicalrecordService medicalrecordService;

    public MedicalrecordController(MedicalrecordService medicalrecordService) {
        this.medicalrecordService = medicalrecordService;
    }

    //ajouter un dossier médical
    @PostMapping("/medicalrecord")
    public void addMedicalrecord(@RequestBody Medicalrecord medicalrecord) {
        medicalrecordService.addMedicalrecord(medicalrecord);
    }

    //mettre à jour un dossier médical existant
    @PutMapping("/medicalrecord")
    public void updateMedicalrecord(@RequestBody Medicalrecord medicalrecord) {
        medicalrecordService.updateMedicalrecord(medicalrecord);
    }

    //supprimer un dossier médical
    @DeleteMapping("/medicalrecord")
    public void deleteMedicalrecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalrecordService.deleteMedicalrecord(firstName, lastName);
    }
}
