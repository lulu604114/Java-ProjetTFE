package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Card;
import com.projet.entities.Dashboard;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.DashboardService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {

    DashboardService service = new DashboardService(Dashboard.class);

    private Dashboard dashboard;
    private User user;

    @PostConstruct
    public void init() {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        dashboard = service.getDashboard(user);
    }

    public boolean isToDisplay(List<String> names,String size, Card card) {
        boolean isDisplayed = false;
        for (String name : names) {
            if (name.equals(card.getIcon()) && card.getSize().equals(size)) isDisplayed = true;
        }
        return isDisplayed;
    }

    public Object getCardContent(Card card) {
        return "0";
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
