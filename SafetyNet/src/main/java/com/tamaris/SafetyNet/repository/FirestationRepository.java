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
}
