package com.projet.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
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
@Table(name = "User_Accounts", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "UA.findByUser", query = "SELECT u  FROM UserAccount u WHERE u.user=:user"),
        @NamedQuery(name = "UA.findByCategory", query = "SELECT u FROM UserAccount u WHERE u.user=:user AND u.financialAccount.accountCategory=:accountCategory"),
        @NamedQuery(name = "UA.deleteById", query = "DELETE FROM UserAccount u  WHERE u.user=:user AND u.financialAccount.accountCategory IN :accountCategory")
})
public class UserAccount implements Comparable<UserAccount>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "tax_deductible")
    private double taxDeductible;

    @NotNull
    @Column(name = "private_part")
    private double privatePart;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "ID", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "financialAccount", referencedColumnName = "id", nullable = false)
    private FinancialAccount financialAccount;

    @OneToMany(mappedBy = "userAccount")
    private List<AccountItem> accountItems;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return id == that.id &&
                Double.compare(that.taxDeductible, taxDeductible) == 0 &&
                Double.compare(that.privatePart, privatePart) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taxDeductible, privatePart);
    }

    @Override
    public int compareTo(UserAccount o) {
        if (financialAccount == null || o.financialAccount == null)
            return 0;

        return Integer.valueOf(financialAccount.getCode()).compareTo(Integer.valueOf(o.financialAccount.getCode()));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    public List<AccountItem> getAccountItems() {
        return accountItems;
    }

    public void setAccountItems(List<AccountItem> accountItems) {
        this.accountItems = accountItems;
    }
}
