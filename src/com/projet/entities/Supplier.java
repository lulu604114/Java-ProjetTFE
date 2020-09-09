package com.projet.entities;

import com.projet.enumeration.SupplierPaimentCondition;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
        @NamedQuery(name = "Supplier.findByDefault", query = "SELECT s FROM Supplier s WHERE s.default_sup=:boolean"),
        @NamedQuery(name = "Supplier.findByLabel", query = "SELECT s FROM Supplier s")
})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NotNull
    @Column(name = "LABEL")
    private String label;

    @Column(name = "TVA")
    private String tva;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "IBAN_BIS")
    private String ibanBis;

    @Column(name = "default_sup", columnDefinition = "boolean default 0")
    private Boolean default_sup;

    @Enumerated(EnumType.STRING)
    @Column(name = "paiement_condition")
    private SupplierPaimentCondition paimentCondition;

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

    public Boolean getDefault_sup() {
        return default_sup;
    }

    public void setDefault_sup(Boolean default_sup) {
        this.default_sup = default_sup;
    }

    public SupplierPaimentCondition getPaimentCondition() {
        return paimentCondition;
    }

    public void setPaimentCondition(SupplierPaimentCondition paimentCondition) {
        this.paimentCondition = paimentCondition;
    }
}