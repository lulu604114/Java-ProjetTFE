package com.projet.entities;

import javax.persistence.*;
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
@Table(name = "AccountItems", schema = "jsf_tfe")
public class AccountItem {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private double amount;

    @Column(name = "tax_deductible_percent")
    private double taxDeductible;

    @Column(name = "private_part_percent")
    private double privatePart;




    //MANY TO ONE
    @ManyToOne
    @JoinColumn(name = "financial_account", referencedColumnName = "id", nullable = false)
    private FinancialAccount financialAccount;

    @ManyToOne
    @JoinColumn(name = "financial_year", referencedColumnName = "id", nullable = false)
    private FinancialYear financialYear;

    @ManyToOne
    @JoinColumn(name = "charge", referencedColumnName = "id", nullable = false)
    private Charge charge;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTaxDeductible() {
        return taxDeductible;
    }

    public void setTaxDeductible(double taxDeductiblePercent) {
        this.taxDeductible = taxDeductiblePercent;
    }

    public double getPrivatePart() {
        return privatePart;
    }

    public void setPrivatePart(double privatePartPercent) {
        this.privatePart = privatePartPercent;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountItem that = (AccountItem) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                Double.compare(that.taxDeductible, taxDeductible) == 0 &&
                Double.compare(that.privatePart, privatePart) == 0 &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount, taxDeductible, privatePart);
    }



    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    public FinancialYear getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(FinancialYear financialYear) {
        this.financialYear = financialYear;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }
}
