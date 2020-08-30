package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Card;
import com.projet.entities.Dashboard;
import com.projet.entities.Message;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.DashboardService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        dashboard = new Dashboard();
    }

    public boolean isToDisplay(List<String> names, String size, Card card) {
        boolean isDisplayed = false;
        for (String name : names) {
            if (name.equals(card.getIcon()) && card.getSize().equals(size)) isDisplayed = true;
        }
        return isDisplayed;
    }

    public Object getCardContent(Card card) {
        Object value = new Object();
        switch (card.getIcon()) {
            case "message":
                if (card.getSize().equals("small")) {
                    value = "0";
                } else {
                    List<Message> messages = new ArrayList<Message>();
                    messages.add(new Message(1,
                            "amaury.lapaque@promsocatc.net",
                            LocalDateTime.now().minusDays(9).withHour(8).withMinute(26),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "amaury_lapaque.jpg"));
                    messages.add(new Message(2,
                            "nathan.lambert@promsocatc.net",
                            LocalDateTime.now().minusDays(1).withHour(23).withMinute(12),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "nathan_lambert.jpg"));
                    messages.add(new Message(3,
                            "nathan.lambert@promsocatc.net",
                            LocalDateTime.now().minusDays(1).withHour(23).withMinute(12),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "nathan_lambert.jpg"));
                    messages.add(new Message(4,
                            "lucas.robyns@promsocatc.net",
                            LocalDateTime.now().minusDays(1).withHour(23).withMinute(12),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "lucas_robyns.jpg"));
                    messages.add(new Message(5,
                            "renaud.diana@promsocatc.net",
                            LocalDateTime.now().minusDays(1).withHour(23).withMinute(12),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "avatar.svg"));
                    messages.add(new Message(6,
                            "nathan.lambert@promsocatc.net",
                            LocalDateTime.now().minusDays(1).withHour(23).withMinute(12),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "nathan_lambert.jpg"));
                    messages.add(new Message(7,
                            "xavier.lawarée@promsocatc.net",
                            LocalDateTime.now().minusDays(1).withHour(23).withMinute(12),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "xavier_lawarée.jpg"));
                    messages.add(new Message(8,
                            "roberto.deguglielmo@promsocatc.net",
                            LocalDateTime.now().minusDays(1).withHour(23).withMinute(12),
                            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            "roberto_deguglielmo.jpg"));
                    value = messages;
                }
                break;
            default:
                value = "0";
        }
        return value;
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
