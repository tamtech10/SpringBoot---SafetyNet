package com.tamaris.SafetyNet.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamaris.SafetyNet.model.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
//datahendler kreiramo a olaksamo posao aplikaciji, to je klasa koje ce traziti podatke iz json, cuvati ih i slati kome treba

@Component //anotacija koja omogucava kreiranje samo jednog objekta ove klase
//da se json ne bi ucitavao svaki put ispocetka
public class DataHendler {

    private final Data data;

    public DataHendler(@Value("${data.filepath}") String filePath) { //gets the path to the json file
        this.data = loadData(filePath);
        System.out.println("Data loaded: " + data);
    }

    //profesor traži da izvučemo logiku čitanja JSON-a iz konstruktora u posebnu metodu
    // Zbog čistijeg koda pa metodu samo pozovemo u konstruktor

    private Data loadData(String filePath) {
        Data tempData;
        try {
            ObjectMapper mapper = new ObjectMapper(); //jackson is reading that file

            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

            //getClass - “daj mi informaciju o ovoj klasi” (DataHandler)
            //getClassLoader - zna gde se nalaze fajlovi aplikacije (classpath)-“pretraživač fajlova unutar aplikacije”
            //getResourceAsStream(filePath) - “Nađi mi fajl data.json i daj mi ga kao stream (tok podataka)”
            tempData = mapper.readValue(is, Data.class); //converts it into a data object with three lists
            //Jackson (ObjectMapper) uzima taj stream i kaže: “Pročitaj ovaj JSON i pretvori ga u Java objekat Data”

        } catch (Exception e) {
            // readValue baca gresku, on kaze "ova metoda je opasna, može poći po zlu, moraš je zaštititi!"
            System.out.println("Error reading json file");
            tempData = null;
        }
        return tempData;
    }

    public Data getData() {
        return data;
    }
}
