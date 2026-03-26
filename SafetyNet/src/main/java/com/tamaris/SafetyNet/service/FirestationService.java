package com.tamaris.SafetyNet.service;

import com.tamaris.SafetyNet.model.Firestation;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.repository.FirestationRepository;
import com.tamaris.SafetyNet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;

    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
    }

    //    Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers
    public List<String> phoneNumbers(String station) {

        ArrayList<String> phone = new ArrayList<>(); //pravimo listu koja cuva nadjene brojeve
        List<Person> personA = personRepository.findAllPersons();

        for (Firestation firestation : firestationRepository.findAllFirestations()) {

            if (firestation.getStation().equals(station)) {

                for(Person person : personA) {
                    if (person.getAddress().equals(firestation.getAddress())) {
                        phone.add(person.getPhone());
                    }
                }
            }
        } return phone;
    }
}
