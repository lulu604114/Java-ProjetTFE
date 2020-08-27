package com.projet.controllers;


import com.projet.conf.App;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.Patient;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.PatientService;
import com.projet.utility.Message;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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




    @PostConstruct
    public void onInit()
    {
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