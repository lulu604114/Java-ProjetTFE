package com.projet.entities;

import com.projet.enumeration.ChargeStatus;
import com.projet.enumeration.PaiementMethodEnum;

import javax.persistence.*;
import javax.validation.constraints.Past;
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
        @NamedQuery(name = "Charge.findPayedByUser", query = "SELECT c FROM Charge c WHERE c.user=:user AND c.payed=true"),
        @NamedQuery(name = "Charge.findAllSupplierByUser", query = "SELECT c.supplier FROM Charge c WHERE c.user=:user")
})
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "label")
    private String label;

    @Basic
    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @Basic
    @Column(name = "amount")
    private double amount;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "due_at")
    private Date dueAt;

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
    @JoinColumn(name = "user", referencedColumnName = "ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "supplier", referencedColumnName = "ID", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "diary", referencedColumnName = "id", nullable = false)
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "financial_year", referencedColumnName = "id", nullable = false)
    private FinancialYear financialYear;

    @OneToMany(mappedBy = "charge")
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        return id == charge.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, createdAt, amount, dueAt);
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

    public Object getPaiementMethod() {
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
