package com.projet.controllers;
import com.projet.conf.App;
import com.projet.connection.EMF;
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
    private Patient patient;
    private List<Patient> patients;
    private List<Patient> filteredPatients;
    private Patient patientTemp;
    private Patient selectedPatient;
    private User user;



    @PostConstruct
    public void onInit()
    {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.patients = this.service.getAll();
    }

    public void patientDetail(Patient patient)
    {
        this.patient = patient;
    }
    //Redirection
    public String openRedirection()
    {
        System.out.println("Je reçois le patient :" + patient.getId());
        this.patientTemp = new Patient(this.patient);
        return "/app/patient/viewPatient.xhtml";
    }
    //création d'un nouveau patient
    public String createPatient()
    {
        FacesMessage message = null;
        boolean succes = false;
        PatientService service = new PatientService(Patient.class);
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            Patient patient = service.createPatient(this.patient, this.user);
            service.save(patient);
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
        patient = new Patient();
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout du patient avec succès",patient.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", succes);
        return "app/patient/viewPatient.xhtml";
    }
    //Sauvegarde des modifications d'un patients
    public void save()
    {


        System.out.println("Je reçois le patient temporaire :" + patientTemp.getAdress() + "Le patient est " + patient.getAdress());
        PatientService service = new PatientService(Patient.class);
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            patientTemp.setAdress(patient.getAdress());
            System.out.println("patient temps apres setfield" + patientTemp.getAdress());
            Patient patient = service.save(patientTemp);
            System.out.println("servicesaveok" + patient.getAdress());
            transaction.commit();
            System.out.println("transaction commit ok");

//            message.display(FacesMessage.SEVERITY_INFO, "Modifications réussies");
        } finally {
            if (transaction.isActive()) {
                System.out.println("probleme");
                transaction.rollback();

//                message.display(FacesMessage.SEVERITY_ERROR, "Unknown error");
            }
            service.close();
        }
    }
    //Annulation de l'enregistrement ou de la modification d'un patient
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