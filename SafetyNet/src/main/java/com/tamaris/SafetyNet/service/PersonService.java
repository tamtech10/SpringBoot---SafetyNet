package com.tamaris.SafetyNet.service;

import com.tamaris.SafetyNet.model.Medicalrecord;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.repository.MedicalrecordRepository;
import com.tamaris.SafetyNet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MedicalrecordRepository medicalrecordRepository;

    public PersonService(PersonRepository personRepository, MedicalrecordRepository medicalrecordRepository) {
        this.personRepository = personRepository;
        this.medicalrecordRepository = medicalrecordRepository;
    }


    //*******Cette url doit retourner les adresses mail de tous les habitants de la ville
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


    //******Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse
    public Map<String, Object> getInfoChild(String address) {

        List<Person> persons = personRepository.findAllPersons();
        List<Medicalrecord> records = medicalrecordRepository.findAllMedicalrecords();

        List<Map<String, Object>> children = new ArrayList<>();
        List<Map<String, String>> members = new ArrayList<>();

        for (Person p : persons) {
            if (p.getAddress().equals(address)) {

                Medicalrecord medicalrecord = null;

                for (Medicalrecord mr : records) {
                    if (mr.getFirstName().equals(p.getFirstName()) && mr.getLastName().equals(p.getLastName())) {
                        medicalrecord = mr;
                        break;
                    } //je m'assure que je suis sur la mm personne
                }
                //Si je trouvais un dossier médical pour cette personne, alors je calcul son âge.
                if (medicalrecord != null) {
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //our string into an object
//                    LocalDate birth = LocalDate.parse(medicalrecord.getBirthdate(), formatter);
//                    int age = calculateAge(birth);

                    Integer age = getAge(p);

                    if(age <= 18) {
                        //ako imaju manje ili jednako kreiramo prvi map gde cemo ih skladistiti
                        Map<String, Object> child = new HashMap<>();
                        child.put("firstName", p.getFirstName());
                        child.put("lastName", p.getLastName());
                        child.put("age", age);
                        children.add(child);
                    } else { //ako je odrastao stavljam ga u map pa taj map u listu
                        Map<String, String> member = new HashMap<>();
                        member.put("firstName", p.getFirstName());
                        member.put("lastName", p.getLastName());
                        members.add(member);
                    }
                }
            }
        }

        //skladistimo sve rezultate u jedan map
        Map<String, Object> results = new HashMap<>();
        results.put("Children", children);
        results.put("Members", members);

        return results;
    }

//    //racunamo godine
//    private int calculateAge(LocalDate birthDate) {
//        return Period.between(birthDate, LocalDate.now()).getYears();
//    }

    //metoda kojom racunamo godine
    public Integer getAge(Person person) {
        //uzimamo listu na kojoj nam pisu godine
        List<Medicalrecord> records = medicalrecordRepository.findAllMedicalrecords();
        Medicalrecord medicalrecord = null;

        //trazimo odgovarajuci karton
        for (Medicalrecord mr : records) {
            if(mr.getFirstName().equals(person.getFirstName()) && mr.getLastName().equals(person.getLastName())) {
                medicalrecord = mr;
                break;
            }
        }
            if(medicalrecord == null || medicalrecord.getBirthdate() == null) {
                return null;
            }
        //moramo da formatiramo podatke o rodjendanu jer su u formi String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            try {
                LocalDate birthdate = LocalDate.parse(medicalrecord.getBirthdate(), formatter);
                return Period.between(birthdate, LocalDate.now()).getYears();
            } catch (Exception e) {
                return null;
            }
    }


}
