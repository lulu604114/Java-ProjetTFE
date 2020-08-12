package com.projet.services;

import com.projet.connection.EMF;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.Patient;
import com.projet.entities.User;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author nathan
 * @project JSF-TFE
 * =================================================================
 */
public class PatientService implements Serializable
{
    private static final Logger log = Logger.getLogger(PatientService.class);
    private static final long serialVersionUID = 1L;

    private List<Patient> patients;
    private EntityManager em;
    private Patient patientDB;

    public List<Patient> getByUser(User user)
    {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);
        EntityFinderImpl<Patient> finder = new EntityFinderImpl<>(Patient.class);
        patients = finder.findByNamedQuery("Patient.findByUser", param);
        return patients;
    }

    public boolean create(Patient patient)
    {
        em = EMF.getEM();
        em.getTransaction().begin();
        boolean state=false;
        try {
            em.persist(patient);
            em.getTransaction().commit();
            state = true;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.error("Rollback");
                state = false;
            }
            em.close();
            log.debug("Ok");
        }
        System.out.print("Le Patient Service est ok");
        return state;
    }

    public boolean editPatient(Patient patient) throws Exception
    {
        System.out.println("J'arrive bien dans l'édition avec " + patient.getAdress());
        em = EMF.getEM();
        em.getTransaction().begin();
        boolean status = false;
        try {
            this.patientDB = em.find(Patient.class, patient.getId());
            if (this.patientDB != null) {
                this.patientDB.setEmail(patient.getEmail());
                this.patientDB.setFirstName(patient.getFirstName());
                this.patientDB.setLastName(patient.getLastName());
                this.patientDB.setAdress(patient.getAdress());
                this.patientDB.setBirthdate(patient.getBirthdate());
                this.patientDB.setCity(patient.getCity());
                this.patientDB.setNiss(patient.getNiss());
                this.patientDB.setPhone(patient.getPhone());
                this.patientDB.setPostalCode(patient.getPostalCode());
                this.patientDB.setStreetBox(patient.getStreetBox());
                this.patientDB.setStreetNumber(patient.getStreetNumber());

                em.merge(patientDB);
                em.getTransaction().commit();
                status = true;
            }
        }finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.error("Rollback");
                status = false;
            }
            em.close();
            log.debug("Ok");
        }
        return status;
    }

    public boolean deleteByID(Patient patient) {
        em = EMF.getEM();
        em.getTransaction().begin();
        boolean status = false;
        try{
            Patient patientDB = em.find(Patient.class, patient.getId());
            if (patientDB != null) {
                em.remove(patientDB);
                status = true;
            } else {
                status = false;
            }
            em.getTransaction().commit();

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.error("Rollback");
                status = false;
            }
            em.close();
            log.debug("Ok");
        }
        return status;
    }
}