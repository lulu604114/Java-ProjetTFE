package com.projet.entities;

import com.projet.enumeration.TypeEnum;

import javax.persistence.*;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE
 * Date: 27/01/2020
 * Time: 19:28
 * =================================================================
 */
@Entity
@Table(name = "Contacts", schema = "c3_jsf_tfe")
public class Contact {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "type")
    @Enumerated
    private TypeEnum type;
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
    @ManyToOne
    @JoinColumn(name = "Users_id", referencedColumnName = "id", nullable = false)
    private User user;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(phone, contact.phone) &&
                Objects.equals(type, contact.type) &&
                Objects.equals(adress, contact.adress) &&
                Objects.equals(streetNumber, contact.streetNumber) &&
                Objects.equals(streetBox, contact.streetBox) &&
                Objects.equals(postalCode, contact.postalCode) &&
                Objects.equals(city, contact.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, type, adress, streetNumber, streetBox, postalCode, city);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
