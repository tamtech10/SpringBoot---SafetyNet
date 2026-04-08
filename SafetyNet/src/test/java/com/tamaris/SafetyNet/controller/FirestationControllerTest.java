package com.tamaris.SafetyNet.controller;

import com.tamaris.SafetyNet.dto.FloodDTO;
import com.tamaris.SafetyNet.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FirestationService firestationService;

    // GET /phoneAlert?firestation=3
    // Treba da vrati listu telefona stanovnika koje pokriva stanica 3
    @Test
    void getPhoneNumber() throws Exception{
        mockMvc.perform(get("/phoneAlert?firestation=3"))
                .andExpect(status().isOk());

        List<String> result = firestationService.phoneNumbers("3");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // GET /firestation?stationNumber=3
    // Treba da vrati listu osoba + broj odraslih i dece
    @Test
    void getInfoAboutPeople() throws Exception{
        mockMvc.perform(get("/firestation?stationNumber=3"))
                .andExpect(status().isOk());

        Map<String, Object> result = firestationService.getInfoAboutPeople("3");

        assertNotNull(result);
        // Rezultat mora imati ključeve
        assertTrue(result.containsKey("persons"));
        assertTrue(result.containsKey("adults"));
        assertTrue(result.containsKey("childrens"));

        System.out.println("Adults: " + result.get("adults"));
        System.out.println("Childrens: " + result.get("childrens"));

        assertEquals(8, result.get("adults"));
        assertEquals(3, result.get("childrens"));
    }

    // GET /flood/stations?stations=1,2
    // Treba da vrati sve domove po adresama za stanice 1 i 2
    @Test
    void getFloodInfo() throws Exception{
        mockMvc.perform(get("/flood/stations?stations=1,2"))
                .andExpect(status().isOk());

        Map<String, List<FloodDTO>> result = firestationService.getFloodInfo(List.of("1", "2"));
        assertNotNull(result);
        assertFalse(result.isEmpty());

        System.out.println("Nb d'adresses: " + result.size());
        assertEquals(6, result.size());

        assertTrue(result.containsKey("644 Gershwin Cir"));
        assertTrue(result.containsKey("908 73rd St"));
        assertTrue(result.containsKey("947 E. Rose Dr"));
        assertTrue(result.containsKey("29 15th St"));
        assertTrue(result.containsKey("892 Downing Ct"));
        assertTrue(result.containsKey("951 LoneTree Rd"));
    }

    @Test
    void addFirestation() {
    }

    @Test
    void updateFirestation() {
    }

    @Test
    void deleteFirestation() {
    }
}