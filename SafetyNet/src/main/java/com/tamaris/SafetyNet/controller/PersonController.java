package com.tamaris.SafetyNet.controller;


import com.tamaris.SafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class PersonController {

    @Autowired
    PersonService personService;


    @GetMapping("/communityEmail")
    public List<String> getEmailFromPeopleInSpecificCity(@RequestParam(name="city") String city) {
        return personService.getEmailFromPeopleInSpecificCity(city);
    }

    //Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse
    @GetMapping("/childAlert")
    public Map<String, Object> getInfoChild(@RequestParam(name = "address") String address) {
        return personService.getInfoChild(address);
    }
}
