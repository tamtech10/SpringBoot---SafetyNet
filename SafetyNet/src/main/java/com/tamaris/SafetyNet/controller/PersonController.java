package com.tamaris.SafetyNet.controller;


import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PersonController {

    @Autowired
    PersonService personService;

//    @GetMapping("/get")
//    public List<Person> getPersons() {
//        return personService.getPersons();
//
//    }


    @GetMapping("/communityEmail")
    public List<String> getEmailFromPeopleInSpecificCity(@RequestParam(name="city") String city) {
        return personService.getEmailFromPeopleInSpecificCity(city);
    }
}
