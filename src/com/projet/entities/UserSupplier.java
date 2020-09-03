package com.projet.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 29/08/2020
 * Time: 16:44
 * =================================================================
 */
@Entity
@Table(name = "User_Suppliers", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "US.findByUser", query = "SELECT u FROM UserSupplier u WHERE u.user=:user")
})
public class UserSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier", referencedColumnName = "ID", nullable = false)
    private Supplier supplier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSupplier that = (UserSupplier) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
