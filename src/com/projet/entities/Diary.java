package com.projet.entities;

import javax.persistence.*;
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
@Entity
@Table(name = "Diaries", schema = "jsf_tfe")
public class Diary {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "Label")
    private String label;
    @OneToMany(mappedBy = "diary")
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
        Diary diary = (Diary) o;
        return id == diary.id &&
                Objects.equals(label, diary.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
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
