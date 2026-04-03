package com.tamaris.SafetyNet.controller;


import com.tamaris.SafetyNet.dto.FireDTO;
import com.tamaris.SafetyNet.dto.firstLastNameDTO;
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

    //*******Cette url doit retourner les adresses mail de tous les habitants de la ville(7)
    @GetMapping("/communityEmail")
    public List<String> getEmailFromPeopleInSpecificCity(@RequestParam(name="city") String city) {
        return personService.getEmailFromPeopleInSpecificCity(city);
    }

    //Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse(2)
    @GetMapping("/childAlert")
    public Map<String, Object> getInfoChild(@RequestParam(name = "address") String address) {
        return personService.getInfoChild(address);
    }

    //******Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
    //de pompiers la desservant(4)
    @GetMapping("/fire")
    public FireDTO getFireInfo(@RequestParam(name = "address") String address) {
        return personService.getFireInfo(address);
    }

    //Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
    //posologie, allergies) de chaque habitant
    @GetMapping("/personInfo")
    public List<firstLastNameDTO> getPersonInfo(@RequestParam (name = "firstName") String firstName,
                                                @RequestParam (name = "lastName") String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }
}
