package com.projet.entities;

import javax.persistence.*;
import java.util.Collection;
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
@Table(name = "Suppliers", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Supplier.findByUser", query = "SELECT s FROM Supplier s WHERE s.user=:user"),
        @NamedQuery(name = "Supplier.findByLabel", query = "SELECT s FROM Supplier s WHERE s.user=:user AND s.label LIKE :label")
})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "LABEL")
    private String label;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "ID", nullable = false)
    private User user;

    @Column(name = "TVA")
    private String tva;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "IBAN_BIS")
    private String ibanBis;

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
        Supplier supplier = (Supplier) o;
        return id == supplier.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    public String getTva() {
        return tva;
    }

    public void setTva(String tva) {
        this.tva = tva;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIbanBis() {
        return ibanBis;
    }

    public void setIbanBis(String ibanBis) {
        this.ibanBis = ibanBis;
    }
}