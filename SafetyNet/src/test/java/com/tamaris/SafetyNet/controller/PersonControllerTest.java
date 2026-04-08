package com.tamaris.SafetyNet.controller;

import com.tamaris.SafetyNet.dto.FireDTO;
import com.tamaris.SafetyNet.dto.FirstLastNameDTO;
import com.tamaris.SafetyNet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest  //pokreće celu aplikaciju
@AutoConfigureMockMvc //priprema MockMvc (simulira HTTP pozive)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;  //ovim "šaljemo" HTTP zahteve u testovima

    @Autowired
    private PersonService personService;

    // GET /communityEmail?city=Culver
    // Treba da vrati emailove SVIH stanovnika grada
    @Test
    void getEmailFromPeopleInSpecificCity() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk());


        List<String> result = personService.getEmailFromPeopleInSpecificCity("Culver");
        assertNotNull(result);
        assertEquals(23, result.size()); // znamo da ima tačno 23 osobe u Culveru, na taj nacin izbegavamo da proveravamo za svaki mail pesaka-provera
    }

    // GET /childAlert?address=1509 Culver St
    // Treba da vrati decu (<=18) i ostale članove domaćinstva
    @Test
    void getInfoChild() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());

        Map<String, Object> result = personService.getInfoChild("1509 Culver St");
        assertNotNull(result);

        // Rezultat mora imati ključeve "Children" i "Members"
        assertTrue(result.containsKey("Children"));
        assertTrue(result.containsKey("Members"));

        //PROVERAVAMO OCEKIVANI REZULTAT-provera
        // izvlačimo listu dece iz rezultata
        List<?> children = (List<?>) result.get("Children");
        // izvlačimo listu odraslih iz rezultata
        List<?> members = (List<?>) result.get("Members");

        // očekujemo tačno 2 dece
        assertEquals(2, children.size());
        // očekujemo tačno 3 odrasla
        assertEquals(3, members.size());
    }

    // GET /fire?address=1509 Culver St
    // Treba da vrati broj stanice i listu stanovnika sa medicinskim podacima
    @Test
    void getFireInfo() throws Exception{
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk());

        FireDTO result = personService.getFireInfo("1509 Culver St");
        assertNotNull(result);

        //ocekivana stanica za tu adresu
        assertEquals("3", result.getStation());
        // lista stanara ne sme biti prazna
        assertNotNull(result.getPersons());
        // na adresi 1509 Culver St živi 5 Boyd-provera
        assertFalse(result.getPersons().isEmpty());
    }

    // GET /personInfo?firstName=John&lastName=Boyd
    // Treba da vrati ime, adresu, godine, email i medicinske podatke
    @Test
    void getPersonInfo() throws Exception{
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk());


        List<FirstLastNameDTO> result = personService.getPersonInfo("John", "Boyd");
        assertNotNull(result);
        assertEquals(1, result.size()); // samo jedan John Boyd postoji u JSON-u

        FirstLastNameDTO john = result.get(0);

        assertEquals("John", john.getFirstName());
        assertEquals("Boyd", john.getLastName());
        assertEquals("1509 Culver St", john.getAddress());
        assertEquals("jaboyd@email.com", john.getEmail());
        assertNotNull(john.getMedications());

    }

    @Test
    void addPerson() {
    }

    @Test
    void updatePerson() {
    }

    @Test
    void deletePerson() {
    }
}