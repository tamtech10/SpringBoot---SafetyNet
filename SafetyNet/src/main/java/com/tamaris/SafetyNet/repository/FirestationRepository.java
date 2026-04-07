package com.tamaris.SafetyNet.repository;

import com.tamaris.SafetyNet.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationRepository {


    private final DataHendler dataHendler;

    public FirestationRepository(DataHendler dataHendler) {
        this.dataHendler = dataHendler;
    }

    //list of all firestations
    public List<Firestation> findAllFirestations() {
        return dataHendler.getData().getFirestations();
    }

    //ajout d'un mapping caserne/adresse
    public void addFirestation(Firestation firestation) {
        dataHendler.getData().getFirestations().add(firestation);
    }

    //mettre à jour le numéro de la caserne de pompiers d'une adresse
    public void updateFirestation(Firestation firestation) {
        List<Firestation> firestations = dataHendler.getData().getFirestations();
        for(int f = 0; f < firestations.size(); f++) {
            if(firestations.get(f).getAddress().equals(firestation.getAddress())) {
                firestations.set(f, firestation);
                break;
            }
        }
    }

    //supprimer le mapping d'une caserne ou d'une adresse
    public void deleteFirestation(String station) {
        dataHendler.getData().getFirestations()
                .removeIf(f -> f.getStation().equals(station));
    }



}
