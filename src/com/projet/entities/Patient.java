package com.projet.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author nathan
 * @project projet_atc
 * Date: 18/11/2019
 * =================================================================
 */
@Entity
@Table(name = "Patients", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Patient.findByUser", query = "SELECT p FROM Patient p WHERE p.user=:user"),
        @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
})

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "niss")
    private String niss;
    @Basic
    @Column(name = "adress")
    private String adress;
    @Basic
    @Column(name = "streetNumber")
    private String streetNumber;
    @Basic
    @Column(name = "streetBox")
    private String streetBox;
    @Basic
    @Column(name = "postalCode")
    private String postalCode;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "tiersPayant")
    private boolean tiersPayant;
    @Basic
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Basic
    @Column(name = "active")
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;
    @OneToMany(mappedBy = "patient")
    private List<Information> informations;
    @OneToMany(mappedBy = "patient")
    private List<Meeting> meetings;
    @OneToMany(mappedBy = "patient")
    private List<Billing> billings;
    @OneToMany(mappedBy = "patient")
    private List<ToDo> toDos;

    public Patient(){}

    public Patient(int id, String firstName, String lastName, String email, String niss, String streetNumber, String streetBox, String postalCode, String city, Date birthdate, boolean tiersPayant, String phone, String adress)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.niss = niss;
        this.streetNumber = streetNumber;
        this.streetBox = streetBox;
        this.postalCode =postalCode;
        this.city = city;
        this.birthdate = birthdate;
        this.tiersPayant =tiersPayant;
        this.phone = phone;
        this.adress = adress;
    }

    public Patient(Patient patient) {
        this(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getNiss(),
                patient.getStreetNumber(),
                patient.getStreetBox(),
                patient.getPostalCode(),
                patient.getCity(),
                patient.getBirthdate(),
                patient.isTiersPayant(),
                patient.getPhone(),
                patient.getAdress()
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNiss() {
        return niss;
    }

    public void setNiss(String niss) {
        this.niss = niss;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetBox() {
        return streetBox;
    }

    public void setStreetBox(String streetBox) {
        this.streetBox = streetBox;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTiersPayant() {
        return tiersPayant;
    }

    public void setTiersPayant(boolean tiersPayant) {
        this.tiersPayant = tiersPayant;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                tiersPayant == patient.tiersPayant &&
                active == patient.active &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(niss, patient.niss) &&
                Objects.equals(adress, patient.adress) &&
                Objects.equals(streetNumber, patient.streetNumber) &&
                Objects.equals(streetBox, patient.streetBox) &&
                Objects.equals(postalCode, patient.postalCode) &&
                Objects.equals(city, patient.city) &&
                Objects.equals(phone, patient.phone) &&
                Objects.equals(email, patient.email) &&
                Objects.equals(birthdate, patient.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, niss, adress, streetNumber, streetBox, postalCode, city, phone, email, tiersPayant, birthdate, active);
    }

    public List<Information> getInformations() {
        return informations;
    }

    public void setInformations(List<Information> informations) {
        this.informations = informations;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public List<Billing> getBillings() {
        return billings;
    }

    public void setBillings(List<Billing> billings) {
        this.billings = billings;
    }

    public List<ToDo> getToDos() {
        return toDos;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }
}