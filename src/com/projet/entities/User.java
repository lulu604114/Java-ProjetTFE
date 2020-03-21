package com.projet.entities;

import javax.persistence.*;
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
@Table(name = "Users", schema = "c3_jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "User.findUserByUsername", query = "SELECT u FROM User u WHERE u.username=:username"),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Basic
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Basic
    @Column(name = "inamiNumber")
    private String inamiNumber;
    @Basic
    @Column(name = "iban")
    private String iban;
    @Basic
    @Column(name = "password", nullable = false)
    private String password;
    @Basic
    @Column(name = "email", nullable = false)
    private String email;
    @Basic
    @Column(name = "username", nullable = false)
    private String username;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Basic
    @Column(name = "tva")
    private String tva;
    @Basic
    @Column(name = "active", nullable = false)
    private boolean active;
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
    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<ToDo> toDos;
    @OneToMany(mappedBy = "user")
    private List<Supplier> suppliers;

    public User() {  }

    public User(int id, String firstName, String lastName, String email, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword()
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
        this.password = password;
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
}
