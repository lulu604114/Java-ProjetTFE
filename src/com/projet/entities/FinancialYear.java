package com.projet.entities;

import javax.persistence.*;
import java.util.Date;
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
@Table(name = "FinancialYears", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "FY.FindByUserAndYear", query = "SELECT f FROM FinancialYear f WHERE f.user=:user AND f.beginAt=:year"),
        @NamedQuery(name = "FY.findByUser", query = "SELECT f FROM FinancialYear f WHERE f.user=:user")
})
public class FinancialYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "begin_at")
    private int beginAt;

    @OneToMany(mappedBy = "financialYear")
    private List<AccountItem> accountItems;
    @OneToMany(mappedBy = "financialYear")
    private List<Charge> charges;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "ID", nullable = false)
    private User user;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(int beginAt) {
        this.beginAt = beginAt;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialYear that = (FinancialYear) o;
        return id == that.id &&
                Objects.equals(beginAt, that.beginAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginAt);
    }




    public List<AccountItem> getAccountItems() {
        return accountItems;
    }

    public void setAccountItems(List<AccountItem> accountItems) {
        this.accountItems = accountItems;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
