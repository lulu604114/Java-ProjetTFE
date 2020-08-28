package com.projet.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE
 * Date: 27/01/2020
 * Time: 19:28
 * =================================================================
 */
@Entity
@Table(name = "Dashboards", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Dashboard.findAll", query = "SELECT d FROM Dashboard d"),
        @NamedQuery(name = "Dashboard.findDashboardByUser", query = "SELECT d FROM Dashboard d WHERE d.user=:user"),
})
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "initializeDashboard",
                procedureName = "initializeDashboard",
                parameters = @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "element")
        )
})
public class Dashboard {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "label")
    private String label;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "Dashboards_Cards",
    joinColumns = @JoinColumn(name = "card_id"),
    inverseJoinColumns = @JoinColumn(name = "dashboard_id"))
    private List<Card> cards = new ArrayList<>();

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
        Dashboard dashboard = (Dashboard) o;
        return id == dashboard.id &&
                Objects.equals(label, dashboard.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
