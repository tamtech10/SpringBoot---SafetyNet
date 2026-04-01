package com.tamaris.SafetyNet.service;

import com.tamaris.SafetyNet.dto.FloodDTO;
import com.tamaris.SafetyNet.dto.PersonDTO;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.model.Firestation;
import com.tamaris.SafetyNet.model.Medicalrecord;

import com.tamaris.SafetyNet.repository.FirestationRepository;
import com.tamaris.SafetyNet.repository.MedicalrecordRepository;
import com.tamaris.SafetyNet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalrecordRepository medicalrecordRepository;
    private final PersonService personService;

    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository, MedicalrecordRepository medicalrecordRepository, PersonService personService) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalrecordRepository = medicalrecordRepository;
        this.personService = personService;
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



    //Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante

    //da bi dobili odredjene info o osobama koje zive na toj adresi mi prvo uzimamo adrese koje su pod okriljem odredjenog broja stanice i ovo radimo sa Stream

    public Map<String, Object> getInfoAboutPeople (String station) {
        //Dobijamo listu adresa koje pokriva ta stanica.
        List<String> addressesFirestation = firestationRepository.findAllFirestations().stream() //uzima sve stanice iz JSON-a (lista svih firestations) i  pretvara tu listu u stream(kao pokretna traka u fabrici)
                .filter(f -> f.getStation().equals(station))// filtrira — propušta samo stanice čiji broj odgovara traženom broju(f- jedna stanica i getStation kao njen broj)
                .map(Firestation::getAddress) // transformiše — za svaku stanicu koja je prošla filter, uzmi samo njenu adresu
                .collect(Collectors.toList()); //skuplja sve rezultate nazad u listu

        List<Person> persons = personRepository.findAllPersons().stream()
                .filter(p -> addressesFirestation.contains(p.getAddress()))
                .collect(Collectors.toList());


        int adult = 0;
        int children = 0;
        List<PersonDTO> result = new ArrayList<>();
        //Prolazimo kroz svaku osobu, nalazimo njen dosije i računamo starost.
        for (Person person : persons) {
//
            Integer age = personService.getAge(person);
            if(age != null) {
                if(age > 18) adult++;
                else children++;
            }
            result.add(new PersonDTO(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getPhone()
            ));
        }
        //Pakujemo sve u Map i vraćamo korisniku.
        Map<String, Object> info = new HashMap<>();
        info.put("persons", result);
        info.put("adults", adult);
        info.put("childrens", children);

        return info;
    }




    //Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
    //personnes par adresse
        public Map<String, List<FloodDTO>> getFloodInfo(List<String> stations) {

        }










//    private int calculateAge (String birthdate){
//            //DateTimeFormatter je uputstvo za čitanje-kaze javi u kom formatu treba da cita String iz JSON
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //our string into an object
//            LocalDate birth = LocalDate.parse(birthdate, formatter);
//            return Period.between(birth, LocalDate.now()).getYears();
//        }





}
