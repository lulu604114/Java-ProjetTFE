package com.projet.entities;

import javax.persistence.*;
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
@Table(name = "Dashboard_cards", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "DashboardCard.removeCardFromDashboard", query = "DELETE FROM DashboardCard dc WHERE dc.card=:cardId AND dc.dashboard=:dashboardId"),
})
public class DashboardCard {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "dashboard", referencedColumnName = "id", nullable = false)
    private Dashboard dashboard;
    @ManyToOne
    @JoinColumn(name = "card", referencedColumnName = "id", nullable = false)
    private Card card;

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
        DashboardCard that = (DashboardCard) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
