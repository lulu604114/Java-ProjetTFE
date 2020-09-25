package com.projet.services;
import com.projet.entities.Patient;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;

public class PatientService extends Service<Patient> implements Serializable {
    private static final Logger log = Logger.getLogger(PatientService.class);
    private static final long serialVersionUID = 1L;


    public PatientService() {
        super();
    }

    public List<Patient> getAll() {
        return finder.findByNamedQuery("Patient.findAll", null);
    }

    @Override
    public Patient save(Patient patient) {
        if (patient.getId() == 0) {
            em.persist(patient);
        } else {
            patient = em.merge(patient);
        }
        return patient;
    }
}