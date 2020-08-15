package com.projet.services;
import com.projet.connection.EMF;
import com.projet.dao.EntityFinder;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.Patient;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.PasswordMatcher;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientService extends Service<Patient> implements Serializable {
    private static final Logger log = Logger.getLogger(PatientService.class);
    private static final long serialVersionUID = 1L;


    public PatientService(Class<?> ec) {
        super(ec);
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