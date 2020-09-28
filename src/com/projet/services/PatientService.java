package com.projet.services;
import com.projet.connection.EMF;
import com.projet.dao.EntityFinder;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.Patient;
import com.projet.entities.User;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

public class PatientService extends Service<Patient> implements Serializable {
    private static final Logger log = Logger.getLogger(PatientService.class);
    private static final long serialVersionUID = 1L;

    public PatientService(){
        super();
    }

    public List<Patient> getAll() {
        return finder.findByNamedQuery("Patient.findAll", null);
    }
    public List<Patient> getAllByUser(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);
        return finder.findByNamedQuery("Patient.findByUser",param);
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

    public Patient deletePatient(Patient patient){
        System.out.println("Je delete le patient :" + patient.getFirstName());
        patient.setActive(false);
        return patient;
    }

    public Patient createPatient(Patient patient, User user){
        System.out.println("Je crée le patient :" + patient.getFirstName() + " date " + patient.getBirthdate() );
        patient.setUser(user);
        patient.setActive(true);
        return patient;
    }
}