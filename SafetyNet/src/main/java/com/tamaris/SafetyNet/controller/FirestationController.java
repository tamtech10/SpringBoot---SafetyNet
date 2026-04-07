package com.tamaris.SafetyNet.controller;

import com.tamaris.SafetyNet.dto.FloodDTO;
import com.tamaris.SafetyNet.model.Firestation;
import com.tamaris.SafetyNet.service.FirestationService;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class FirestationController {


    private final FirestationService firestationService;


    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    //    Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers(3)
    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumber(@RequestParam(name="firestation") String firestation) {
        return firestationService.phoneNumbers(firestation);
    }

    //Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante(1)
    @GetMapping("/firestation")
    public Map<String, Object> getInfoAboutPeople(@RequestParam(name = "stationNumber") String stationNb) {
        return firestationService.getInfoAboutPeople(stationNb);
    }

    //Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
   //personnes par adresse(5)
    @GetMapping("flood/stations")
    public Map<String, List<FloodDTO>> getFloodInfo(@RequestParam(name = "stations") String stationNb) {
        List<String> stations = Arrays.asList(stationNb.split(","));
        return firestationService.getFloodInfo(stations);
    }

    //ajout d'un mapping caserne/adresse
    @PostMapping("/firestation")
    public void addFirestation(@RequestBody Firestation firestation) {
        firestationService.addFirestation(firestation);
    }

    //mettre à jour le numéro de la caserne de pompiers d'une adresse
    @PutMapping("/firestation")
    public void updateFirestation(@RequestBody  Firestation firestation) {
        firestationService.updateFirestation(firestation);
    }

    //supprimer le mapping d'une caserne ou d'une adresse
    @DeleteMapping("/firestation")
    public void deleteFirestation(@RequestParam String station) {
        firestationService.deleteFirestation(station);
    }

}
