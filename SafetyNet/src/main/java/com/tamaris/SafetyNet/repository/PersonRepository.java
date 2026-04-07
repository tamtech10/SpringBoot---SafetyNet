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

    //ajouter une nouvelle personne
    public void addPerson(Person person) { //prima ceo person objekat jer dodajemo novi
        dataHendler.getData().getPersons().add(person);
    }

    //mettre à jour une personne existante
    public void updatePerson(Person person) { //radimo sa boucle for jer je tako lakse kada menjamo elemente u listi(sa sat menjamo element na odredjenoj poziciji)
        List<Person> persons = dataHendler.getData().getPersons();
        for(int p = 0; p < persons.size(); p ++) {
            if(persons.get(p).getFirstName().equals(person.getFirstName())
            && persons.get(p).getLastName().equals(person.getLastName())) {
                persons.set(p, person);
                break;
            }
        }
    }

    //supprimer une personne
    public void deletePerson(String firstName, String lastName) { //string je zato sto ne prima citav objekat nego samo ime i prezime
        //ovde opet koristimo stream jer brisemo pa nam je praktican
        dataHendler.getData().getPersons()
                .removeIf(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
    }




}
