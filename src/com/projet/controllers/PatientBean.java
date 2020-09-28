package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Billing;
import com.projet.entities.Meeting;
import com.projet.entities.Patient;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.MeetingService;
import com.projet.services.PatientService;
import com.projet.utils.Message;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    PatientService service = new PatientService();
    private Patient patient;
    private List<Patient> patients;
    private List<Meeting> meetings;
    private List<Patient> filteredPatients;
    private Patient patientTemp;
    private Patient selectedPatient;
    private User user;

    @PostConstruct
    public void onInit()
    {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.patients = this.service.getAllByUser(user);
        edit();
    }

    /**
     * Redirection with selected patient
     * @return
     */
    public String openRedirection()
    {
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
        PatientService service = new PatientService(Patient.class);
        EntityTransaction transaction = this.service.getTransaction();
        this.transaction.begin();
        try {
            patientTemp.setFields(patient);
            service.save(patientTemp);
            transaction.commit();
            message.display(FacesMessage.SEVERITY_INFO, "Modifications réussies");
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                message.display(FacesMessage.SEVERITY_ERROR, "Unknown error");
            }
            service.close();
        }

    }

    /**
     * Clone patient for update
     */
    public void edit()
    {
        this.patientTemp = patient.clone();
    }

    /**
     * make patient inactive
     * @param patient
     */
    public void deletePatient(Patient patient)
    {
        PatientService service = new PatientService(Patient.class);
        EntityTransaction transaction = this.service.getTransaction();
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
     * Get appointment by user
     * @return
     */
    public List<Meeting> getPastAppointment(){
        MeetingService meetingService = new MeetingService(Meeting.class);
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        //LocalDateTime startOfWeek = LocalDateTime.now().withHour(0).withMinute(0).withSecond(1);
        LocalDateTime startOfWeek = LocalDateTime.of(2020, 01, 01,00,00);
        //LocalDateTime endOfWeek = LocalDateTime.now().with(fieldISO, 7).withHour(23).withMinute(59).withSecond(59).plusDays(1);
        LocalDateTime endOfWeek = LocalDateTime.now();
        this.meetings = meetingService. getMeetingsByPatient(startOfWeek, endOfWeek,this.user,  patient);
        return meetings;
    }
    public List<Meeting> getFuturAppointment(){
        MeetingService meetingService = new MeetingService(Meeting.class);
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        LocalDateTime startOfWeek = LocalDateTime.now().withHour(0).withMinute(0).withSecond(1);
        LocalDateTime endOfWeek = LocalDateTime.of(2050,12,31,00,00);
        this.meetings = meetingService. getMeetingsByPatient(startOfWeek, endOfWeek,this.user,  patient);
        return meetings;
    }

    /**
     * reset var patient
     */
    public void reset(){
        this.patient = new Patient();
    }
    /**
     * Cancel an creation, update or delete
     */
    public void cancel() {
        message.display(FacesMessage.SEVERITY_WARN, "Annulation", "Aucunes modifications réalisées");
    }

    /**
     * get the invoices
     * @return
     */
    public List<Billing> getInvoices(){
        List<Billing> invoices = new ArrayList<Billing>();
        invoices.add(new Billing(1,
                                   "123456789",
                                    new Date(),
                                    patient,
                                    "Facture de la scéance"));
        return invoices;
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

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }


}