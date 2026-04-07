package com.tamaris.SafetyNet.service;

import com.tamaris.SafetyNet.dto.FireDTO;
import com.tamaris.SafetyNet.dto.FloodDTO;
import com.tamaris.SafetyNet.dto.FirstLastNameDTO;
import com.tamaris.SafetyNet.model.Firestation;
import com.tamaris.SafetyNet.model.Medicalrecord;
import com.tamaris.SafetyNet.model.Person;
import com.tamaris.SafetyNet.repository.FirestationRepository;
import com.tamaris.SafetyNet.repository.MedicalrecordRepository;
import com.tamaris.SafetyNet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MedicalrecordRepository medicalrecordRepository;
    private final FirestationRepository firestationRepository;

    public PersonService(PersonRepository personRepository, MedicalrecordRepository medicalrecordRepository, FirestationRepository firestationRepository) {
        this.personRepository = personRepository;
        this.medicalrecordRepository = medicalrecordRepository;
        this.firestationRepository = firestationRepository;
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




    //******Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
    //de pompiers la desservant
    public FireDTO getFireInfo(String address) {

        String station = firestationRepository.findAllFirestations().stream() //preko adrese nadjemo broj stanice koja pokriva
                .filter(f -> f.getAddress().equals(address))
                .map(Firestation::getStation) //stream trazi String jer je jedna info
                .findFirst()//stream moze da nadje vise rezultata, stream kaze uzmi prvi koji nadjes
                .orElse(null); //ako vrednost ne postoji vrati null

        List<Person> persons = personRepository.findAllPersons().stream()
                .filter(p -> p.getAddress().equals(address)) //koristimo adresu koju imamo i skupljaju osobe koje zive na toj adresi
                .collect(Collectors.toList());


        List<FloodDTO> floodList = new ArrayList<>();

        for (Person p : persons) {

            Medicalrecord record = medicalrecordRepository.findByNameLastname(p.getFirstName(), p.getLastName());
            Integer age = getAge(p);

            if (age == null || record == null) continue;
            FloodDTO fDTO = new FloodDTO(
                    p.getFirstName(),
                    p.getLastName(),
                    p.getPhone(),
                    age,
                    record.getMedications(),
                    record.getAllergies()
            );
               floodList.add(fDTO); //vracamo listu sa informacijama iz floodDTO
        }

        return new FireDTO(station, floodList); //vracamo novi dto sa nasim info

    }


    //Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
    //posologie, allergies) de chaque habitant

    public List<FirstLastNameDTO> getPersonInfo(String firstName, String lastName) {

        //nadji sve osobe sa tim imenom i prezimenom
        List<Person> per = personRepository.findAllPersons().stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .collect(Collectors.toList());

        List<FirstLastNameDTO> result = new ArrayList<>(); //Napravi praznu listu rezultata

        for(Person p : per) {

            Medicalrecord record = medicalrecordRepository.findByNameLastname(p.getFirstName(), p.getLastName());

            Integer age = getAge(p);
            if (age == null || record == null) continue;

            FirstLastNameDTO fLDTO = new FirstLastNameDTO(
                    p.getFirstName(),
                    p.getLastName(),
                    p.getAddress(),
                    age,
                    p.getEmail(),
                    record.getMedications(),
                    record.getAllergies()
            );
                result.add(fLDTO);

        } return result;
    }

    //*****CRUD
    //ajouter une nouvelle personne
    public void addPerson(Person person) {
        personRepository.addPerson(person);
    }

    //mettre à jour une personne existante
    public void updatePerson(Person person) {
        personRepository.updatePerson(person);
    }

    //supprimer une personne
    public void deletePerson(String firstName, String lastName) {
        personRepository.deletePerson(firstName,lastName);
    }


}
