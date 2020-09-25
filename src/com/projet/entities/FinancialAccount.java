package com.projet.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 28/08/2020
 * Time: 22:37
 * =================================================================
 */
@Entity
@Table(name = "FinancialAccounts", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "FA.findDefaultFa", query = "SELECT f FROM FinancialAccount  f WHERE f.defaultFa=:boolean")
})
public class FinancialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Pattern(regexp = "^((?!(0))[0-9]{1,6})$", message = "Doit être compris entre 1 et 999999")
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "label")
    private String label;

    @Column(name = "default_tax_deductible")
    private Double defaultTaxDeductible;

    @Column(name = "default_private_part")
    private Double defaultPrivatePart;

    @NotNull
    @Column(name = "redeemable")
    private boolean redeemable;

    @NotNull
    @Column(name = "default_fa", columnDefinition = "boolean default 0")
    private boolean defaultFa;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    private AccountCategory accountCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getDefaultTaxDeductible() {
        return defaultTaxDeductible;
    }

    public void setDefaultTaxDeductible(Double defaultTaxDeductible) {
        this.defaultTaxDeductible = defaultTaxDeductible;
    }

    public Double getDefaultPrivatePart() {
        return defaultPrivatePart;
    }

    public void setDefaultPrivatePart(Double defaultPrivatePart) {
        this.defaultPrivatePart = defaultPrivatePart;
    }

    public boolean isRedeemable() {
        return redeemable;
    }

    public void setRedeemable(boolean redeemable) {
        this.redeemable = redeemable;
    }

    public boolean isDefaultFa() {
        return defaultFa;
    }

    public void setDefaultFa(boolean defaultFa) {
        this.defaultFa = defaultFa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialAccount that = (FinancialAccount) o;
        return id == that.id &&
                redeemable == that.redeemable &&
                Objects.equals(code, that.code) &&
                Objects.equals(label, that.label) &&
                Objects.equals(defaultTaxDeductible, that.defaultTaxDeductible) &&
                Objects.equals(defaultPrivatePart, that.defaultPrivatePart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label, defaultTaxDeductible, defaultPrivatePart, redeemable);
    }

    public AccountCategory getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(AccountCategory accountCategory) {
        this.accountCategory = accountCategory;
    }
}
