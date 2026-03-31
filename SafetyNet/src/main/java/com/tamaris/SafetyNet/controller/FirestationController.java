package com.tamaris.SafetyNet.controller;

import com.tamaris.SafetyNet.service.FirestationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class FirestationController {


    private final FirestationService firestationService;


    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    //    Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers
    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumber(@RequestParam(name="firestation") String firestation) {
        return firestationService.phoneNumbers(firestation);
    }

    @GetMapping("/firestation")
    public Map<String, Object> getInfoAboutPeople(@RequestParam(name = "stationNumber") String stationNb) {
        return firestationService.getInfoAboutPeople(stationNb);
    }
}
