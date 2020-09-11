package com.projet.controllers;
import com.projet.conf.App;
import com.projet.entities.Patient;
import com.projet.entities.User;
import com.projet.services.PatientService;
import com.projet.security.SecurityManager;
import org.primefaces.PrimeFaces;
import com.projet.utils.Message;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

/**
 * @author nathan
 *
 */
@Named("patientBean")
@SessionScoped
public class PatientBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    PatientService service = new PatientService(Patient.class);
    EntityTransaction transaction = this.service.getTransaction();
    private Patient patient;
    private List<Patient> patients;
    private List<Patient> filteredPatients;
    private Patient patientTemp;
    private Patient selectedPatient;
    private User user;
    private Patient patientDB;
    private Patient patientDb;

    @PostConstruct
    public void onInit()
    {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.patients = this.service.getAllByUser(user);
    }

    public String openRedirection()
    {
        System.out.println("Je reçois le patient :" + patient.getId());
        this.patientTemp = new Patient(this.patient);
        return "/app/patient/viewPatient.xhtml";
    }

    /**
     * Create patient
     * @return
     */
    public String createPatient()
    {
        FacesMessage message = null;
        boolean succes = false;
        PatientService service = new PatientService(Patient.class);
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            Patient patient = service.createPatient(this.patient, this.user);
            this.patient = service.save(patient);
            transaction.commit();
            System.out.println("Je commit");
            succes = true;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                succes = false;
            }
            service.close();
        }
        this.patients.add(this.patient);
        patient = new Patient();
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout du patient avec succès",patient.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", succes);
        return "app/patient/viewPatient.xhtml";
    }

    /**
     * Update a patient
     */
    public void save()
    {
        this.transaction.begin();
        try {
            patientTemp.setFields(patient);
            service.save(patientTemp);
            transaction.commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            service.close();
        }

    }
    public void deletePatient(Patient patient)
    {
        this.transaction.begin();
        try {
            Patient patientDeleted = service.deletePatient(patient);
            service.save(patientDeleted);
            transaction.commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            service.close();
        }
        this.patients.remove(patient);
    }

    /**
     * Cancel an creation, update or delete
     */
    public void cancel() {
        message.display(FacesMessage.SEVERITY_WARN, "Annulation", "Aucunes modifications réalisées");
    }

    /* Getter and Setter */
    public PatientBean()
    {
        patient = new Patient();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public Patient getPatientTemp() {
        return patientTemp;
    }

    public void setPatientTemp(Patient patientTemp) {
        this.patientTemp = patientTemp;
    }

    public List<Patient> getFilteredPatients() {
        return filteredPatients;
    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    public void setFilteredPatients(List<Patient> filteredPatients) {
        this.filteredPatients = filteredPatients;
    }
}