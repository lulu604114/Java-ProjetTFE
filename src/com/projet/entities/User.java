package com.projet.entities;

import com.projet.enumeration.Language;
import com.projet.enumeration.UserStatus;
import com.projet.enumeration.UserTitle;
import com.projet.security.SecurityManager;
import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

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
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "addUserFinancialAccount",
                procedureName = "addUserDFFinancialAccount",
                parameters = @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "user")
        ),
        @NamedStoredProcedureQuery(
                name = "addUserSupplier",
                procedureName = "addUserDFSupplier",
                parameters = @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "user")
        ),
        @NamedStoredProcedureQuery(
                name = "addUserDiary",
                procedureName = "addUserDFDiary",
                parameters = @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "user")
        )
})
public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NotNull
    @Column(columnDefinition = "boolean default 1", name = "ACTIVE")
    private boolean active;

    @Column(name = "FIRSTNAME")
    @NotEmpty
    @Size(min = 1, max = 250)
    private String firstName;

    @Column(name = "LASTNAME")
    @NotEmpty
    @Size(min = 1, max = 250)
    private String lastName;

    @Column(name = "INAMINUMBER")
    @Nullable
    @Size(min = 8, max = 12)
    private String inamiNumber;

    @Column(name = "IBAN")
    @Nullable
    @Size(min = 16, max = 16)
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?$")
    private String iban;

    @Column(name = "PASSWORD")
    @NotEmpty
    @Size(min = 1, max = 100)
    private String password;

    @Column(name = "EMAIL")
    @NotEmpty
    @Size(min = 6, max = 120)
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @Column(name = "USERNAME")
    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "PHONE")
    @Nullable
    @Size(min = 10, max = 13)
    private String phone;

    @Column(name = "MOBILE")
    @Nullable
    @Size(min = 10, max = 13)
    private String mobile;

    @Column(name = "TVA")
    @Nullable
    @Size(min = 4, max = 50)
    private String tva;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDATE")
    @Nullable
    private Date birthdate;

    @Column(name = "charge_config_set")
    private boolean chargeConfigSet;


    // ENUMERATION
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'", name = "TITLE")
    private UserTitle title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'", name = "LANGUAGE")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'", name = "STATUS")
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
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Dashboard> dashboards;
    @OneToMany(mappedBy = "user")
    private List<Document> documents;
    @OneToMany(mappedBy = "user")
    private List<Place> places;
    @OneToMany(mappedBy = "user")
    private List<ToDo> toDos;
    @OneToMany(mappedBy = "user")
    private List<Supplier> suppliers;
    @OneToMany(mappedBy = "user")
    private List<AccountCategory> accountCategories;
    @OneToMany(mappedBy = "user")
    private List<Diary> diaries;
    @OneToMany(mappedBy = "user")
    private List<FinancialAccount> financialAccounts;
    @OneToMany(mappedBy = "user")
    private List<FinancialYear> financialYears;


    // ManyToOne
    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private Role role;




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

    public boolean isChargeConfigSet() {
        return chargeConfigSet;
    }

    public void setChargeConfigSet(boolean chargeConfigSet) {
        this.chargeConfigSet = chargeConfigSet;
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
        if (getCharges() == null)
            setCharges(new ArrayList<>());

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
        if (getSuppliers() == null)
            setSuppliers(new ArrayList<>());

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

    public List<AccountCategory> getAccountCategories() {
        return accountCategories;
    }

    public void setAccountCategories(List<AccountCategory> accountCategories) {
        this.accountCategories = accountCategories;
    }

    public List<Diary> getDiaries() {
        return diaries;
    }

    public void setDiaries(List<Diary> diaries) {
        this.diaries = diaries;
    }

    public List<FinancialAccount> getFinancialAccounts() {
        return financialAccounts;
    }

    public void setFinancialAccounts(List<FinancialAccount> financialAccounts) {
        this.financialAccounts = financialAccounts;
    }

    public List<FinancialYear> getFinancialYears() {
        return financialYears;
    }

    public void setFinancialYears(List<FinancialYear> financialYears) {
        this.financialYears = financialYears;
    }

    public FinancialYear addFinancialYear(FinancialYear financialYear) {
        if (getFinancialYears() == null)
            setFinancialYears(new ArrayList<>());

        getFinancialYears().add(financialYear);
        financialYear.setUser(this);

        return financialYear;
    }

    public FinancialYear removeFinancialYear(FinancialYear financialYear) {
        getFinancialYears().remove(financialYear);
        financialYear.setUser(null);

        return financialYear;
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
