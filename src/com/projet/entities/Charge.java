package com.projet.entities;

import com.projet.enumeration.ChargeStatus;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Date;
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
@Table(name = "Charges")
@NamedQueries({
        @NamedQuery(name = "Charge.findByUser", query = "SELECT c FROM Charge c WHERE c.user=:user"),
        @NamedQuery(name = "Charge.findPayedByUser", query = "SELECT c FROM Charge c WHERE c.user=:user AND c.payed=true"),
        @NamedQuery(name = "Charge.findAllSupplierByUser", query = "SELECT c.supplier FROM Charge c WHERE c.user=:user")
})
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String label;

    @Past
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private double amount;

    @Temporal(TemporalType.DATE)
    private Date dueAt;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "supplier", nullable = false)
    private Supplier supplier;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChargeStatus status;

    private boolean payed;

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

}
