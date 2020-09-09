package com.projet.entities;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author nathan
 * @project projet_atc
 * Date: 14/08/2020
 * =================================================================
 */
@Entity
@Table(name = "Patients", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Patient.findByUser", query = "SELECT p FROM Patient p WHERE p.user=:user AND p.active=true"),
        @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
})

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @NotEmpty
    @Size(min = 1, max = 250)
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @NotEmpty
    @Size(min = 1, max = 250)
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Nullable
    @Column(name = "niss")
    private String niss;
    @Basic
    @Nullable
    @Column(name = "adress")
    private String adress;
    @Basic
    @Nullable
    @Column(name = "streetNumber")
    private String streetNumber;
    @Basic
    @Nullable
    @Column(name = "streetBox")
    private String streetBox;
    @Basic
    @Nullable
    @Column(name = "postalCode")
    private String postalCode;
    @Basic
    @Nullable
    @Column(name = "city")
    private String city;
    @Basic
    @Nullable
    @Size(min = 10, max = 13)
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    @Size(min = 6, max = 120)
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
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
    private List<Document> documents;
    @OneToMany(mappedBy = "patient")
    private List<Information> informations;
    @OneToMany(mappedBy = "patient")
    private List<Meeting> meetings;
    @OneToMany(mappedBy = "patient")
    private List<Billing> billings;
    @OneToMany(mappedBy = "patient")
    private List<ToDo> toDos;

    public Patient(){}

    public Patient(int id, String firstName, String lastName, String email, String niss, String streetNumber, String streetBox, String postalCode, String city, Date birthdate, boolean tiersPayant, String phone, String adress, boolean active)
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
        this.active = active;

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
                patient.getAdress(),
                patient.isActive()
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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void setFields(Patient patient) {
        this.firstName = patient.firstName;
        this.lastName = patient.lastName;
        this.birthdate = patient.birthdate;
        this.niss = patient.niss;
        this.adress = patient.adress;
        this.streetNumber = patient.streetNumber;
        this.streetBox = patient.streetBox;
        this.postalCode = patient.postalCode;
        this.city = patient.city;
        this.phone = patient.phone;
        this.email = patient.email;
        this.tiersPayant = patient.tiersPayant;
        this.birthdate = patient.birthdate;
        this.active = patient.active;
        this.user = patient.user;
    }

}