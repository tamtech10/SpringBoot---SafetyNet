package com.tamaris.SafetyNet.controller;


import com.tamaris.SafetyNet.dto.FireDTO;
import com.tamaris.SafetyNet.dto.FirstLastNameDTO;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

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
    //posologie, allergies) de chaque habitant(6)
    @GetMapping("/personInfo")
    public List<FirstLastNameDTO> getPersonInfo(@RequestParam (name = "firstName") String firstName,
                                                @RequestParam (name = "lastName") String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }

    //ajouter une nouvelle personne
    @PostMapping("/person")
    public void addPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    //mettre à jour une personne existante
    @PutMapping("/person")
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
    }

    //supprimer une personne
    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);
    }
}
