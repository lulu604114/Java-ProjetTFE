package com.projet.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 24/08/2020
 * Time: 11:47
 * =================================================================
 */
@Entity
@Table(name = "FinancialAccounts", schema = "jsf_tfe")
public class FinancialAccount {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "label")
    private String label;
    @Basic
    @Column(name = "tax_deductible_percent")
    private double taxDeductible;
    @Basic
    @Column(name = "private_part_percent")
    private double privatePart;
    @Basic
    @Column(name = "redeemable")
    private boolean redeemable;
    @OneToMany(mappedBy = "financialAccount")
    private List<AccountItem> accountItems;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "ID", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    private AccountCategory category;




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

    public double getTaxDeductible() {
        return taxDeductible;
    }

    public void setTaxDeductible(double taxDeductible) {
        this.taxDeductible = taxDeductible;
    }

    public double getPrivatePart() {
        return privatePart;
    }

    public void setPrivatePart(double privatePart) {
        this.privatePart = privatePart;
    }

    public boolean isRedeemable() {
        return redeemable;
    }

    public void setRedeemable(boolean redeemable) {
        this.redeemable = redeemable;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialAccount that = (FinancialAccount) o;
        return id == that.id &&
                Double.compare(that.taxDeductible, taxDeductible) == 0 &&
                Double.compare(that.privatePart, privatePart) == 0 &&
                redeemable == that.redeemable &&
                Objects.equals(code, that.code) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label, taxDeductible, privatePart, redeemable);
    }




    public List<AccountItem> getAccountItems() {
        return accountItems;
    }

    public void setAccountItems(List<AccountItem> accountItems) {
        this.accountItems = accountItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccountCategory getCategory() {
        return category;
    }

    public void setCategory(AccountCategory category) {
        this.category = category;
    }
}
