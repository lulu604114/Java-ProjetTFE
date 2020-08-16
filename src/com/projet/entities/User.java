package com.projet.entities;

import com.projet.enumeration.Language;
import com.projet.enumeration.UserStatus;
import com.projet.enumeration.UserTitle;
import com.projet.security.SecurityManager;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
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
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findUserByEmail", query = "SELECT u FROM User u WHERE u.email=:email")
})
public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private UserTitle title;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String inamiNumber;

    private String iban;

    @NotEmpty
    @Pattern(regexp = "^(?=.{8,}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$", message = "")
    private String password;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.{4,20}$)(?:[a-zA-Z\\d]+(?:[._][a-zA-Z\\d])*)+$", message = "")
    private String username;

    private String phone;

    @NotEmpty
    @Pattern(regexp = "(0|\\+32)[1-9]( *[0-9]{2}){4}", message = "Format incorrect")
    private String mobile;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private String tva;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private List<Charge> charges;

    @OneToMany(mappedBy = "user")
    private List<Patient> patients;

    @OneToMany(mappedBy = "user")
    private List<Connection> connections;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Dashboard> dashboards;

    @OneToMany(mappedBy = "user")
    private List<Document> documents;

    @OneToMany(mappedBy = "user")
    private List<Place> places;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<ToDo> toDos;

    @OneToMany(mappedBy = "user")
    private List<Supplier> suppliers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserTitle getTitle() {
        return title;
    }

    public void setTitle(UserTitle title) {
        this.title = title;
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

    public String getInamiNumber() {
        return inamiNumber;
    }

    public void setInamiNumber(String inamiNumber) {
        this.inamiNumber = inamiNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = SecurityManager.encryptPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getTva() {
        return tva;
    }

    public void setTva(String tva) {
        this.tva = tva;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    public Charge addCharge(Charge charge) {
        getCharges().add(charge);
        charge.setUser(this);

        return charge;
    }

    public Charge removeCharge(Charge charge) {
        getCharges().remove(charge);
        charge.setUser(null);

        return charge;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public Dashboard addDashboard(Dashboard dashboard) {
        if (getDashboards() == null)
            setDashboards(new ArrayList<>());

        getDashboards().add(dashboard);
        dashboard.setUser(this);

        return dashboard;
    }

    public Dashboard removeDashboard(Dashboard dashboard) {
        getDashboards().remove(dashboard);
        dashboard.setUser(null);

        return dashboard;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ToDo> getToDos() {
        return toDos;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Supplier addSupplier(Supplier supplier) {
        getSuppliers().add(supplier);
        supplier.setUser(this);

        return supplier;
    }

    public Supplier removeSupplier(Supplier supplier) {
        getSuppliers().remove(supplier);
        supplier.setUser(null);

        return supplier;
    }

    public List<Patient> getPatients() {
        return patients;
    }

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
