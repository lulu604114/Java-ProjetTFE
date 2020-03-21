package com.projet.entities;

import javax.persistence.*;
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
@Table(name = "Dashboards", schema = "c3_jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Dashboard.findDashboardByUser", query = "SELECT d FROM Dashboard d WHERE d.user.id=:userId"),
})
public class Dashboard {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "label")
    private String label;
    @OneToMany(mappedBy = "dashboard")
    private List<DashboardCard> dashboardCards;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
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
        Dashboard dashboard = (Dashboard) o;
        return id == dashboard.id &&
                Objects.equals(label, dashboard.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    public List<DashboardCard> getDashboardCards() {
        return dashboardCards;
    }

    public void setDashboardCards(List<DashboardCard> dashboardCards) {
        this.dashboardCards = dashboardCards;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
