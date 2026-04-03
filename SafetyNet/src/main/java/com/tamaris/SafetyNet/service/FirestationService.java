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
        List<String> addressesFirestation = getAddressesByStation(station); //pozivamo kreiranu metodu

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

//metoda koja pokazuje koje adrese pokriva neka stanica
    public List<String> getAddressesByStation(String station) {
        return firestationRepository.findAllFirestations().stream()//uzima sve stanice iz JSON-a (lista svih firestations) i  pretvara tu listu u stream(kao pokretna traka u fabrici)
                .filter(f -> f.getStation().equals(station))// filtrira — propušta samo stanice čiji broj odgovara traženom broju(f- jedna stanica i getStation kao njen broj)
                .map(Firestation::getAddress) // transformiše — za svaku stanicu koja je prošla filter, uzmi samo njenu adresu
                .collect(Collectors.toList()); //skuplja sve rezultate nazad u listu
    }


//    //Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
//    //personnes par adresse
        public Map<String, List<FloodDTO>> getFloodInfo(List<String> stations) {
//            List<String> addressesFirestation = getAddressesByStation(station); //ne mozemo samo da pozovemo metodu ovako jer primamo listu stanica
                                                                                    //i moramo tu listu da procesljamo(ovo je kada je prima samo broj jedne stanice

            List<String> allAddresses = new ArrayList<>(); //imamo listu stanica,treba da prodjemo sve i skupimo sve adrese

            for (String station : stations) {
                List<String> addresses = getAddressesByStation(station);
                allAddresses.addAll(addresses); // dodaj sve adrese te stanice u glavnu listu, addAll dodaje sve elemente jedne liste u drugu
                                                //to je metoda klase ArrayList (odnosno List) u Javi
            }

            List<Person> persons = personRepository.findAllPersons().stream()
                    .filter(p -> allAddresses.contains(p.getAddress()))
                    .collect(Collectors.toList());

            Map<String, List<FloodDTO>> result = new HashMap<>(); //imas listu person koje zive na odredjenim adresama
            //pravis map gde skladistis rezultate jer treba da imas adresa: pa sve info o osobi(zato je lista u map)
            for (Person per : persons) { //sada prolazis kroz svaku osobu i za nju pravis DTO(jer smo tu stavili samo info koje nam trebaju)

                Integer age = personService.getAge(per);//uzimamo godine preko metode koju vec imamo

                Medicalrecord record = medicalrecordRepository.findByNameLastname(per.getFirstName(), per.getLastName());//uzimamo ime i prezime preko metode koju vec imamo

                if (age == null || record == null) continue; //ako osoba nema medicinski dosije? record može biti null i onda će pući

                FloodDTO dto = new FloodDTO( //Ovde kreiramo novi FloodDTO objekat i punimo ga svim podacima koje smo prikupili
                        per.getFirstName(),
                        per.getLastName(),
                        per.getPhone(),
                        age,
                        record.getMedications(), //ovde bi pukao kod da nismo stavili uslov pre dto
                        record.getAllergies()
                );

                if (!result.containsKey(per.getAddress())) { //Dodajemo u mapu pod ključem adrese
                    result.put(per.getAddress(), new ArrayList<>());
                }
                result.get(per.getAddress()).add(dto);
            }
                return result;
            }









        }










//    private int calculateAge (String birthdate){
//            //DateTimeFormatter je uputstvo za čitanje-kaze javi u kom formatu treba da cita String iz JSON
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //our string into an object
//            LocalDate birth = LocalDate.parse(birthdate, formatter);
//            return Period.between(birth, LocalDate.now()).getYears();
//        }






