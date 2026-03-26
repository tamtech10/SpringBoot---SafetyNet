package com.tamaris.SafetyNet.controller;

import com.tamaris.SafetyNet.model.Firestation;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class FirestationController {


    private final FirestationService firestationService;


    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumber(@RequestParam(name="firestation") String firestation) {
        return firestationService.phoneNumbers(firestation);
    }
}
