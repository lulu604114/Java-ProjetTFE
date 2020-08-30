package com.projet.entities;

import javax.persistence.*;
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
        @NamedQuery(name = "UA.findByUser", query = "SELECT u  FROM UserAccount u WHERE u.user=:user")
})
public class UserAccount implements Comparable<UserAccount>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "tax_deductible")
    private double taxDeductible;
    @Basic
    @Column(name = "private_part")
    private double privatePart;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "ID", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "financialAccount", referencedColumnName = "id", nullable = false)
    private FinancialAccount financialAccount;

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
        if (financialAccount.getCode() == null || o.financialAccount.getCode() == null)
            return 0;

        return financialAccount.getCode().compareTo(o.financialAccount.getCode());
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
}
