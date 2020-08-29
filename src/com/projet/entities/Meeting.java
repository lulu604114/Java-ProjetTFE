package com.projet.entities;

import com.projet.enumeration.MeetingTypeEnum;
import com.projet.enumeration.UserTitle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;


/**
 * The type Meeting.
 *
 * @author Amaury  29/08/2020 - 19:54 Projet TFE
 */
@Entity
@Table(name = "Meetings", schema = "jsf_tfe")
public class Meeting {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic
    @Column(name = "description")
    private String description;

    // ENUMERATION
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'", name = "title")
    private MeetingTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "medicalService", referencedColumnName = "id", nullable = true)
    private MedicalService medicalService;
    @ManyToOne
    @JoinColumn(name = "place", referencedColumnName = "id", nullable = true)
    private Place place;
    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "bill", referencedColumnName = "id", nullable = true)
    private Billing bill;

    /**
     * Instantiates a new Meeting.
     */
    public Meeting() {
    }

    /**
     * Instantiates a new Meeting.
     *
     * @param id          the id
     * @param title       the title
     * @param startDate   the start date
     * @param endDate     the end date
     * @param description the description
     * @param type        the type
     * @param patient     the patient
     */
    public Meeting(int id, String title, Date startDate, Date endDate, String description, MeetingTypeEnum type, Patient patient) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.patient = patient;
    }

    /**
     * Instantiates a new Meeting.
     *
     * @param id             the id
     * @param title          the title
     * @param startDate      the start date
     * @param endDate        the end date
     * @param description    the description
     * @param type           the type
     * @param medicalService the medical service
     * @param place          the place
     * @param patient        the patient
     * @param bill           the bill
     */
    public Meeting(int id, String title, Date startDate, Date endDate, String description, MeetingTypeEnum type, MedicalService medicalService, Place place, Patient patient, Billing bill) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.medicalService = medicalService;
        this.place = place;
        this.patient = patient;
        this.bill = bill;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public MeetingTypeEnum getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(MeetingTypeEnum type) {
        this.type = type;
    }

    /**
     * Gets medical service.
     *
     * @return the medical service
     */
    public MedicalService getMedicalService() {
        return medicalService;
    }

    /**
     * Sets medical service.
     *
     * @param medicalService the medical service
     */
    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }

    /**
     * Gets place.
     *
     * @return the place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Sets place.
     *
     * @param place the place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * Gets patient.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets patient.
     *
     * @param patient the patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Gets bill.
     *
     * @return the bill
     */
    public Billing getBill() {
        return bill;
    }

    /**
     * Sets bill.
     *
     * @param bill the bill
     */
    public void setBill(Billing bill) {
        this.bill = bill;
    }
}
