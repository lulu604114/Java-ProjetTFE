package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Dashboard;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.DashboardService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("dashboard")
@SessionScoped
public class DashboardBean implements Serializable {

    DashboardService service = new DashboardService(Dashboard.class);

    private Dashboard dashboard;
    private User user;

    @PostConstruct
    public void init() {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        dashboard = service.getDashboard(user);
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
