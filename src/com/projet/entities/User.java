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
 * @author lucas Lapaque
 * @project TFE  Date: 27/01/2020 Time: 19:28 =================================================================
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
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(columnDefinition = "boolean default 1", name = "active")
    private boolean active;

    @Column(name = "first_name")
    @NotEmpty
    @Size(min = 1, max = 250)
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @Size(min = 1, max = 250)
    private String lastName;

    @Column(name = "inami_number")
    @Nullable
    @Size(min = 8, max = 12)
    private String inamiNumber;

    @Column(name = "iban")
    @Nullable
    @Size(min = 16, max = 16)
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?$")
    private String iban;

    @Column(name = "password")
    @NotEmpty
    @Size(min = 1, max = 100)
    private String password;

    @Column(name = "email")
    @NotEmpty
    @Size(min = 6, max = 120)
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @Column(name = "username")
    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "phone")
    @Nullable
    @Size(min = 10, max = 13)
    private String phone;

    @Column(name = "mobile")
    @Nullable
    @Size(min = 10, max = 13)
    private String mobile;

    @Column(name = "tva")
    @Nullable
    @Size(min = 4, max = 50)
    private String tva;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    @Nullable
    private Date birthdate;

    @Column(name = "charge_config_set")
    private boolean chargeConfigSet;

    @Column(columnDefinition = "varchar(255) default 'avatar.svg'", name = "avatar")
    private String avatar;


    // ENUMERATION
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'", name = "title")
    private UserTitle title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'", name = "language")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'NONE'", name = "status")
    private UserStatus status;

    // OneToMany
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Charge> charges;
    @OneToMany(mappedBy = "user")
    private List<Patient> patients;
    @OneToMany(mappedBy = "user")
    private List<Connection> connections;
    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Dashboard> dashboards;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Meeting> meetings;
    @OneToMany(mappedBy = "user")
    private List<Document> documents;
    @OneToMany(mappedBy = "user")
    private List<Place> places;
    @OneToMany(mappedBy = "user")
    private List<ToDo> toDos;
    @OneToMany(mappedBy = "user")
    private List<AccountCategory> accountCategories;
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Diary> diaries;
    @OneToMany(mappedBy = "user")
    private List<FinancialYear> financialYears;
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<UserAccount> userAccounts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<UserSupplier> userSuppliers;

    // ManyToOne
    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private Role role;


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
    public UserTitle getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(UserTitle title) {
        this.title = title;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets inami number.
     *
     * @return the inami number
     */
    public String getInamiNumber() {
        return inamiNumber;
    }

    /**
     * Sets inami number.
     *
     * @param inamiNumber the inami number
     */
    public void setInamiNumber(String inamiNumber) {
        this.inamiNumber = inamiNumber;
    }

    /**
     * Gets iban.
     *
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets iban.
     *
     * @param iban the iban
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = SecurityManager.encryptPassword(password);
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets mobile.
     *
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets mobile.
     *
     * @param mobile the mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets birthdate.
     *
     * @return the birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Sets birthdate.
     *
     * @param birthdate the birthdate
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Gets tva.
     *
     * @return the tva
     */
    public String getTva() {
        return tva;
    }

    /**
     * Sets tva.
     *
     * @param tva the tva
     */
    public void setTva(String tva) {
        this.tva = tva;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Is charge config set boolean.
     *
     * @return the boolean
     */
    public boolean isChargeConfigSet() {
        return chargeConfigSet;
    }

    /**
     * Sets charge config set.
     *
     * @param chargeConfigSet the charge config set
     */
    public void setChargeConfigSet(boolean chargeConfigSet) {
        this.chargeConfigSet = chargeConfigSet;
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Gets charges.
     *
     * @return the charges
     */
    public List<Charge> getCharges() {
        return charges;
    }

    /**
     * Sets charges.
     *
     * @param charges the charges
     */
    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets avatar.
     *
     * @param avatar the avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Add charge charge.
     *
     * @param charge the charge
     *
     * @return the charge
     */
    public Charge addCharge(Charge charge) {
        if (getCharges() == null)
            setCharges(new ArrayList<>());

        getCharges().add(charge);
        charge.setUser(this);

        return charge;
    }

    /**
     * Remove charge charge.
     *
     * @param charge the charge
     *
     * @return the charge
     */
    public Charge removeCharge(Charge charge) {
        getCharges().remove(charge);
        charge.setUser(null);

        return charge;
    }

    /**
     * Gets connections.
     *
     * @return the connections
     */
    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * Sets connections.
     *
     * @param connections the connections
     */
    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    /**
     * Gets contacts.
     *
     * @return the contacts
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Sets contacts.
     *
     * @param contacts the contacts
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Gets dashboards.
     *
     * @return the dashboards
     */
    public List<Dashboard> getDashboards() {
        return dashboards;
    }

    /**
     * Sets dashboards.
     *
     * @param dashboards the dashboards
     */
    public void setDashboards(List<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    /**
     * Add dashboard dashboard.
     *
     * @param dashboard the dashboard
     *
     * @return the dashboard
     */
    public Dashboard addDashboard(Dashboard dashboard) {
        if (this.getDashboards() == null)
            setDashboards(new ArrayList<>());

        this.getDashboards().add(dashboard);
        dashboard.setUser(this);

        return dashboard;
    }

    /**
     * Remove dashboard dashboard.
     *
     * @param dashboard the dashboard
     *
     * @return the dashboard
     */
    public Dashboard removeDashboard(Dashboard dashboard) {
        getDashboards().remove(dashboard);
        dashboard.setUser(null);

        return dashboard;
    }

    /**
     * Add meetings meeting.
     *
     * @param meeting the meeting
     *
     * @return the meeting
     */
    public Meeting addMeetings(Meeting meeting) {
        if (this.getMeetings() == null)
            setMeetings(new ArrayList<>());

        this.getMeetings().add(meeting);
        meeting.setUser(this);

        return meeting;
    }

    /**
     * Remove meeting meeting.
     *
     * @param meeting the meeting
     *
     * @return the meeting
     */
    public Meeting removeMeeting(Meeting meeting) {
        this.getMeetings().remove(meeting);
        meeting.setUser(null);

        return meeting;
    }

    /**
     * Gets meetings.
     *
     * @return the meetings
     */
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * Sets meetings.
     *
     * @param meetings the meetings
     */
    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    /**
     * Gets documents.
     *
     * @return the documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets documents.
     *
     * @param documents the documents
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Gets places.
     *
     * @return the places
     */
    public List<Place> getPlaces() {
        return places;
    }

    /**
     * Sets places.
     *
     * @param places the places
     */
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets to dos.
     *
     * @return the to dos
     */
    public List<ToDo> getToDos() {
        return toDos;
    }

    /**
     * Sets to dos.
     *
     * @param toDos the to dos
     */
    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    /**
     * Gets patients.
     *
     * @return the patients
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Sets patients.
     *
     * @param patients the patients
     */
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    /**
     * Gets account categories.
     *
     * @return the account categories
     */
    public List<AccountCategory> getAccountCategories() {
        return accountCategories;
    }

    /**
     * Sets account categories.
     *
     * @param accountCategories the account categories
     */
    public void setAccountCategories(List<AccountCategory> accountCategories) {
        this.accountCategories = accountCategories;
    }

    /**
     * Gets diaries.
     *
     * @return the diaries
     */
    public List<Diary> getDiaries() {
        return diaries;
    }

    /**
     * Sets diaries.
     *
     * @param diaries the diaries
     */
    public void setDiaries(List<Diary> diaries) {
        this.diaries = diaries;
    }

    /**
     * Add diary diary.
     *
     * @param diary the diary
     *
     * @return the diary
     */
    public Diary addDiary(Diary diary) {
        if (getDiaries() == null)
            setDiaries(new ArrayList<>());

        getDiaries().add(diary);
        diary.setUser(this);

        return diary;
    }

    /**
     * Gets financial years.
     *
     * @return the financial years
     */
    public List<FinancialYear> getFinancialYears() {
        return financialYears;
    }

    /**
     * Sets financial years.
     *
     * @param financialYears the financial years
     */
    public void setFinancialYears(List<FinancialYear> financialYears) {
        this.financialYears = financialYears;
    }

    /**
     * Add financial year financial year.
     *
     * @param financialYear the financial year
     *
     * @return the financial year
     */
    public FinancialYear addFinancialYear(FinancialYear financialYear) {
        if (getFinancialYears() == null)
            setFinancialYears(new ArrayList<>());

        getFinancialYears().add(financialYear);
        financialYear.setUser(this);

        return financialYear;
    }

    /**
     * Remove financial year financial year.
     *
     * @param financialYear the financial year
     *
     * @return the financial year
     */
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
     *
     * @param user the user
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

    /**
     * Gets user accounts.
     *
     * @return the user accounts
     */
    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    /**
     * Sets user accounts.
     *
     * @param userAccounts the user accounts
     */
    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    /**
     * Gets user suppliers.
     *
     * @return the user suppliers
     */
    public List<UserSupplier> getUserSuppliers() {
        return userSuppliers;
    }

    /**
     * Sets user suppliers.
     *
     * @param userSuppliers the user suppliers
     */
    public void setUserSuppliers(List<UserSupplier> userSuppliers) {
        this.userSuppliers = userSuppliers;
    }
}
