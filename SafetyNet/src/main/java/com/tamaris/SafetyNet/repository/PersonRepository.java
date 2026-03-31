package com.tamaris.SafetyNet.repository;


import com.tamaris.SafetyNet.dto.PersonDTO;
import com.tamaris.SafetyNet.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {


    private final DataHendler dataHendler;

    public PersonRepository(DataHendler dataHendler) {
        this.dataHendler = dataHendler;
    }
    //list of all persons
    public List<Person> findAllPersons() {
        return  dataHendler.getData().getPersons();
    }

    //Cette url doit retourner les adresses mail de tous les habitants de la ville

//    public ArrayList<String> getEmailFromPeopleInSpecificCity(String city) {
//
//        Data newData = dataHendler.getData();
//        ArrayList<String> emails = new ArrayList<>();
//
//        List<Person> personA = newData.getPersons();
//        for (Person person : personA) {
//            if (person.getCity().equals(city)) {
//                emails.add(person.getEmail());
//            }
//        }
//        return emails;
//    }




}
