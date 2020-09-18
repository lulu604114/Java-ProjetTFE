package com.projet.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Collection;
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
@NamedQueries({
        @NamedQuery(name = "AI.findByUserAccountAndFinancialYear", query = "SELECT a FROM AccountItem a WHERE a.userAccount=:userAccount AND a.financialYear=:financialYear")
})
@Entity
@Table(name = "AccountItems", schema = "jsf_tfe", catalog = "")
public class AccountItem implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @NotNull
    @NotEmpty
    @Column(name = "description")
    private String description;
    
    @Positive
    @Column(name = "amount")
    private double amount;

    @Column(name = "tax_deductible_percent")
    private double taxDeductible;

    @Column(name = "private_part_percent")
    private double privatePart;



    //MANY TO ONE
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_account", referencedColumnName = "id", nullable = false)
    private UserAccount userAccount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "financial_year", referencedColumnName = "id", nullable = false)
    private FinancialYear financialYear;

    @ManyToOne
    @JoinColumn(name = "charge", referencedColumnName = "id", nullable = false)
    private Charge charge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", referencedColumnName = "id")
    private AccountItem parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountItem> accountItems;

    @Transient
    private int redeemableYear;

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

    @Override
    public AccountItem  clone() throws CloneNotSupportedException {
        AccountItem accountItem = (AccountItem) super.clone();

        accountItem.setUserAccount(getUserAccount());
        accountItem.setFinancialYear(getFinancialYear());
        accountItem.setCharge(getCharge());

        return accountItem;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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

    public AccountItem getParent() {
        return parent;
    }

    public void setParent(AccountItem parent) {
        this.parent = parent;
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

        accountItem.setParent(this);
        getAccountItems().add(accountItem);

        return accountItem;
    }

    public int getRedeemableYear() {
        return redeemableYear;
    }

    public void setRedeemableYear(int redeemableYear) {
        this.redeemableYear = redeemableYear;
    }
}
