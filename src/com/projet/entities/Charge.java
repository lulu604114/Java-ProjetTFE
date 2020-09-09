package com.projet.entities;

import com.projet.enumeration.ChargeStatus;
import com.projet.enumeration.PaiementMethodEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
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
@Table(name = "Charges", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Charge.findByUser", query = "SELECT c FROM Charge c WHERE c.user=:user"),
        @NamedQuery(name = "Charge.findByUserOrderByDate", query = "SELECT c FROM Charge c WHERE c.user=:user ORDER BY c.dueAt DESC"),
        @NamedQuery(name = "Charge.findPayedByUser", query = "SELECT c FROM Charge c WHERE c.user=:user AND c.payed=true"),
})
public class Charge implements Comparable<Charge>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Veuillez renseigner ce champs")
    @NotEmpty
    @Column(name = "label")
    private String label;

    @NotNull (message = "Veuillez renseigner ce champ")
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @NotNull (message = "Veuillez renseigner ce champ")
    @Positive (message = "Le montant doit être strictement supérieur à zéro")
    @Column(name = "amount")
    private double amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_at")
    private Date dueAt;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChargeStatus status;

    @Column(name = "payed")
    private boolean payed;

    @Column(name = "free_communication")
    private String freeCommunication;

    @Column(name = "structered_communication")
    private String structeredCommunication;

    @Column(name = "paiement_method")
    @Enumerated(EnumType.STRING)
    private PaiementMethodEnum paiementMethod;

    @Column(name = "payed_at")
    @Temporal(TemporalType.DATE)
    private Date payedAt;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_supplier", referencedColumnName = "id", nullable = false)
    private UserSupplier userSupplier;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "diary", referencedColumnName = "id", nullable = false)
    private Diary diary;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "financial_year", referencedColumnName = "id", nullable = false)
    private FinancialYear financialYear;

    @OneToMany(mappedBy = "charge", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<AccountItem> accountItems;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDueAt() {
        return dueAt;
    }

    public void setDueAt(Date dueAt) {
        this.dueAt = dueAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSupplier getUserSupplier() {
        return userSupplier;
    }

    public void setUserSupplier(UserSupplier userSupplier) {
        this.userSupplier = userSupplier;
    }

    public ChargeStatus getStatus() {
        return status;
    }

    public void setStatus(ChargeStatus status) {
        this.status = status;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return id == charge.id &&
                Double.compare(charge.amount, amount) == 0 &&
                payed == charge.payed &&
                label.equals(charge.label) &&
                createdAt.equals(charge.createdAt) &&
                Objects.equals(dueAt, charge.dueAt) &&
                status == charge.status &&
                Objects.equals(freeCommunication, charge.freeCommunication) &&
                Objects.equals(structeredCommunication, charge.structeredCommunication) &&
                paiementMethod == charge.paiementMethod &&
                Objects.equals(payedAt, charge.payedAt) &&
                Objects.equals(user, charge.user) &&
                userSupplier.equals(charge.userSupplier) &&
                diary.equals(charge.diary) &&
                financialYear.equals(charge.financialYear) &&
                Objects.equals(accountItems, charge.accountItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, createdAt, amount, dueAt, status, payed, freeCommunication, structeredCommunication, paiementMethod, payedAt, user, userSupplier, diary, financialYear, accountItems);
    }

    @Override
    public int compareTo(Charge o) {
        if (createdAt == null || o.createdAt == null)
            return 0;

        return createdAt.compareTo(o.createdAt);
    }

    public String getFreeCommunication() {
        return freeCommunication;
    }

    public void setFreeCommunication(String freeCommunication) {
        this.freeCommunication = freeCommunication;
    }

    public String getStructeredCommunication() {
        return structeredCommunication;
    }

    public void setStructeredCommunication(String structeredCommunication) {
        this.structeredCommunication = structeredCommunication;
    }

    public PaiementMethodEnum getPaiementMethod() {
        return paiementMethod;
    }

    public void setPaiementMethod(PaiementMethodEnum paiementMethod) {
        this.paiementMethod = paiementMethod;
    }

    public Date getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(Date payedAt) {
        this.payedAt = payedAt;
    }

    public List<AccountItem> getAccountItems() {
        return accountItems;
    }

    public void setAccountItems(List<AccountItem> accountItems) {
        this.accountItems = accountItems;
    }

    public AccountItem addAccountItem(AccountItem accountItem) {
        if (getAccountItems() == null)
            setAccountItems(new ArrayList<>());

        getAccountItems().add(accountItem);
        accountItem.setCharge(this);

        return accountItem;
    }

    public AccountItem removeAccountItem(AccountItem accountItem) {
        getAccountItems().remove(accountItem);
        accountItem.setCharge(null);

        return accountItem;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public FinancialYear getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(FinancialYear financialYear) {
        this.financialYear = financialYear;
    }
}
