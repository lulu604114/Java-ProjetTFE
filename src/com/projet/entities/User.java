package com.projet.entities;

import com.projet.enumeration.Language;
import com.projet.enumeration.UserStatus;
import com.projet.enumeration.UserTitle;
import com.projet.security.SecurityManager;
import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "Users", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "User.findUserByUsername", query = "SELECT u FROM User u WHERE u.username=:username"),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @NotNull
    @Column(columnDefinition = "boolean default 1")
    private boolean active;

    @Basic
    @NotNull
    @Size(min = 1, max = 250)
    private String firstName;

    @Basic
    @NotNull
    @Size(min = 1, max = 250)
    private String lastName;

    @Basic
    @Nullable
    @Size(min = 8, max = 12)
    private String inamiNumber;

    @Basic
    @Nullable
    @Size(min = 16, max = 16)
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?$")
    private String iban;

    @Basic
    @NotNull
    @Size(min = 1, max = 100)
    private String password;

    @Basic
    @NotNull
    @Size(min = 6, max = 120)
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @Basic
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Basic
    @Nullable
    @Size(min = 10, max = 13)
    private String phone;

    @Basic
    @Nullable
    @Size(min = 10, max = 13)
    private String mobile;

    @Basic
    @Nullable
    @Size(min = 4, max = 50)
    private String tva;

    @Temporal(TemporalType.DATE)
    @Nullable
    private Date birthdate;

    // ENUMERATION

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'")
    private UserTitle title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'")
    private UserStatus status;

    // OneToMany
    @OneToMany(mappedBy = "user")
    private List<Charge> charges;

    @OneToMany(mappedBy = "user")
    private List<Patient> patients;

    @OneToMany(mappedBy = "user")
    private List<Connection> connections;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    @OneToMany(mappedBy = "user")
    private List<Dashboard> dashboards;

    @OneToMany(mappedBy = "user")
    private List<Document> documents;

    @OneToMany(mappedBy = "user")
    private List<Place> places;

    @OneToMany(mappedBy = "user")
    private List<ToDo> toDos;

    @OneToMany(mappedBy = "user")
    private List<Supplier> suppliers;

    // ManyToOne
    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private Role role;

    /**
     * getId
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * setId
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getTitle
     * @return
     */
    public UserTitle getTitle() {
        return title;
    }

    /**
     * setTitle
     * @param title
     */
    public void setTitle(UserTitle title) {
        this.title = title;
    }

    /**
     * getFirstName
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setFirstName
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getLastName
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setLastName
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getInamiNumber
     * @return
     */
    public String getInamiNumber() {
        return inamiNumber;
    }

    /**
     * setInamiNumber
     * @param inamiNumber
     */
    public void setInamiNumber(String inamiNumber) {
        this.inamiNumber = inamiNumber;
    }

    /**
     * getIban
     * @return
     */
    public String getIban() {
        return iban;
    }

    /**
     * setIban
     * @param iban
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * getPassword
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * setPassword
     * @param password
     */
    public void setPassword(String password) {
        this.password = SecurityManager.encryptPassword(password);
    }

    /**
     * getEmail
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getUsername
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * setUsername
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getPhone
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setPhone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getMobile
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * setMobile
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * getBirthdate
     * @return
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * setBirthdate
     * @param birthdate
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * getTva
     * @return
     */
    public String getTva() {
        return tva;
    }

    /**
     * setTva
     * @param tva
     */
    public void setTva(String tva) {
        this.tva = tva;
    }

    /**
     * isActive
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     * setActive
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * getLanguage
     * @return
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * setLanguage
     * @param language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * getStatus
     * @return
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * setStatus
     * @param status
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * getCharges
     * @return
     */
    public List<Charge> getCharges() {
        return charges;
    }

    /**
     * setCharges
     * @param charges
     */
    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    /**
     * addCharge
     * @param charge
     * @return
     */
    public Charge addCharge(Charge charge) {
        getCharges().add(charge);
        charge.setUser(this);

        return charge;
    }

    /**
     * removeCharge
     * @param charge
     * @return
     */
    public Charge removeCharge(Charge charge) {
        getCharges().remove(charge);
        charge.setUser(null);

        return charge;
    }

    /**
     * getConnections
     * @return
     */
    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * setConnections
     * @param connections
     */
    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    /**
     * getContacts
     * @return
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * setContacts
     * @param contacts
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * getDashboards
     * @return
     */
    public List<Dashboard> getDashboards() {
        return dashboards;
    }

    /**
     * setDashboards
     * @param dashboards
     */
    public void setDashboards(List<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    /**
     * getDocuments
     * @return
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * setDocuments
     * @param documents
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * getPlaces
     * @return
     */
    public List<Place> getPlaces() {
        return places;
    }

    /**
     * setPlaces
     * @param places
     */
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    /**
     * getRole
     * @return
     */
    public Role getRole() {
        return role;
    }

    /**
     * setRole
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * getToDos
     * @return
     */
    public List<ToDo> getToDos() {
        return toDos;
    }

    /**
     * setToDos
     * @param toDos
     */
    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    /**
     * getSuppliers
     * @return
     */
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    /**
     * setSuppliers
     * @param suppliers
     */
    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    /**
     * addSupplier
     * @param supplier
     * @return
     */
    public Supplier addSupplier(Supplier supplier) {
        getSuppliers().add(supplier);
        supplier.setUser(this);

        return supplier;
    }

    /**
     * removeSupplier
     * @param supplier
     * @return
     */
    public Supplier removeSupplier(Supplier supplier) {
        getSuppliers().remove(supplier);
        supplier.setUser(null);

        return supplier;
    }

    /**
     * getPatients
     * @return
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * setPatients
     * @param patients
     */
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                active == user.active &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(inamiNumber, user.inamiNumber) &&
                Objects.equals(iban, user.iban) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(birthdate, user.birthdate) &&
                Objects.equals(tva, user.tva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, inamiNumber, iban, password, email, username, phone, birthdate, tva, active);
    }

    @Override
    public User clone() {

        User user = null;

        try {
            user = (User) super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }

        return user;
    }

    /**
     * setUserFields
     * @param user
     */
    public void setFields(User user) {
        this.title = user.title;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.inamiNumber = user.inamiNumber;
        this.iban = user.iban;
        this.password = user.password;
        this.email = user.email;
        this.username = user.username;
        this.phone = user.phone;
        this.birthdate = user.birthdate;
        this.tva = user.tva;
        this.active = user.active;
        this.mobile = user.mobile;
        this.status = user.status;
        this.language = user.language;
    }
}
