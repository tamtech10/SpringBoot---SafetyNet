package com.tamaris.SafetyNet.controller;

import com.tamaris.SafetyNet.model.Firestation;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class FirestationController {

    @Autowired
    FirestationService firestationService;

//    @GetMapping("/get")
//    public List<Firestation> getFirestations() {
//        return firestationService.getFirestations();
//
//    }
}
