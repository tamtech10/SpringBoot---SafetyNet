package com.tamaris.SafetyNet.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalrecordRepository {

    @Autowired
    private DataHendler dataHendler;
}
