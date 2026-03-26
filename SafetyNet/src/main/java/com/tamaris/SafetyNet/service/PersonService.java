package com.tamaris.SafetyNet.service;

import com.tamaris.SafetyNet.model.Data;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    //Cette url doit retourner les adresses mail de tous les habitants de la ville
    public ArrayList<String> getEmailFromPeopleInSpecificCity(String city) {

        ArrayList<String> emails = new ArrayList<>();

        List<Person> personA = personRepository.findAllPersons();
        for (Person person : personA) {
            if (person.getCity().equals(city)) {
                emails.add(person.getEmail());
            }
        }
        return emails;
    }

//    public ArrayList<String> getEmailFromPeopleInSpecificCity(String city) {
//        return personRepository.getEmailFromPeopleInSpecificCity(city);
//    }
}
