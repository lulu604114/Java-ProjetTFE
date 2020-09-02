package com.projet.entities;

import com.projet.enumeration.MeetingTypeEnum;

import javax.persistence.*;
import java.util.Calendar;

/**
 * The type Meeting.
 *
 * @author Amaury 29/08/2020 - 19:54 Projet TFE
 */
@Entity
@Table(name = "Meetings", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Meeting.findAll", query = "SELECT m FROM Meeting m"),
})
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
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startDate;
    @Basic
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endDate;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(nullable = false, columnDefinition = "tinyint(1) default 0", name = "all_day")
    private boolean allDay;

    // ENUMERATION
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'", name = "type")
    private MeetingTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "medicalService", referencedColumnName = "id", nullable = true)
    private MedicalService medicalService;
    @ManyToOne
    @JoinColumn(name = "place", referencedColumnName = "id", nullable = true)
    private Place place;
    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = true)
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "bill", referencedColumnName = "id", nullable = true)
    private Billing bill;

    /**
     * Instantiates a new Meeting.
     */
    public Meeting() {
        this.type = MeetingTypeEnum.APPOINTMENT;
        this.allDay = false;
    }

    /**
     * Instantiates a new Meeting.
     *
     * @param id          the id
     * @param title       the title
     * @param startDate   the start date
     * @param endDate     the end date
     * @param description the description
     * @param allDay      the all day
     * @param type        the type
     * @param patient     the patient
     */
    public Meeting(int id, String title, Calendar startDate, Calendar endDate, String description, boolean allDay, MeetingTypeEnum type, Patient patient) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.allDay = allDay;
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
    public Meeting(int id, String title, Calendar startDate, Calendar endDate, String description, MeetingTypeEnum type, MedicalService medicalService, Place place, Patient patient, Billing bill) {
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
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * Is all day boolean.
     *
     * @return the boolean
     */
    public boolean isAllDay() {
        return allDay;
    }

    /**
     * Sets all day.
     *
     * @param allDay the all day
     */
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Calendar endDate) {
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
