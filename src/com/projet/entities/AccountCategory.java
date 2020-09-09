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
 * Date: 28/08/2020
 * Time: 22:37
 * =================================================================
 */
@Entity
@Table(name = "AccountCategories", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "AC.findByUser", query = "SELECT DISTINCT u.financialAccount.accountCategory FROM UserAccount u WHERE u.user=:user"),
        @NamedQuery(name = "AC.findDefault", query = "SELECT a FROM AccountCategory a WHERE a.user=null")
})
public class AccountCategory implements Comparable<AccountCategory>{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "label")
    private String label;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "ID", nullable = true)
    private User user;

    @Transient
    private List<UserAccount> userAccounts;

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

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
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
        AccountCategory that = (AccountCategory) o;
        return id == that.id &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    @Override
    public int compareTo(AccountCategory o) {
        if (getLabel().isEmpty() || o.getLabel().isEmpty())
            return 0;

        return getLabel().compareTo(o.getLabel());
    }
}
