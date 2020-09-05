package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.DashboardService;
import com.projet.services.MeetingService;
import com.projet.utils.DateManager;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * The type Dashboard bean.
 *
 * @author Amaury Lapaque
 */
@Named("dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {

    /**
     * The Dashboard service.
     */
    DashboardService dashboardService = new DashboardService(Dashboard.class);
    /**
     * The Meeting service.
     */
    MeetingService meetingService = new MeetingService(Meeting.class);

    private Dashboard dashboard;
    private List<Meeting> meetings = new ArrayList<Meeting>();
    private User user;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.dashboard = dashboardService.getDashboard(user);
    }

    /**
     * Is to display boolean.
     *
     * @param names the names
     * @param size  the size
     * @param card  the card
     *
     * @return the boolean
     */
    public boolean isToDisplay(List<String> names, String size, Card card) {
        boolean isDisplayed = false;
        for (String name : names) {
            if (name.equals(card.getIcon()) && card.getSize().equals(size)) isDisplayed = true;
        }
        return isDisplayed;
    }

    /**
     * Gets card content.
     *
     * @param card the card
     *
     * @return the card content
     */
    public Object getCardContent(Card card) {
        Object value = new Object();
        switch (card.getIcon().toLowerCase()) {
            case "message": {
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

                if (card.getSize().equals("small")) {
                    value = messages.size();
                } else {
                    value = messages;
                }
            }
            break;
            case "calendar": {
                TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
                LocalDateTime startOfWeek = LocalDateTime.now().withHour(0).withMinute(0).withSecond(1);
                LocalDateTime endOfWeek = LocalDateTime.now().with(fieldISO, 7).withHour(23).withMinute(59).withSecond(59).plusDays(1);
                if (card.getSize().equals("small")) {
                    value = this.meetingService.getMeetingsForCard(startOfWeek, endOfWeek, this.user).size();
                } else if (card.getSize().equals("large")) {
                    value = this.meetingService.getMeetingsForCard(startOfWeek, endOfWeek, this.user);
                }
            }
            break;
            case "task": {
                TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
                LocalDateTime startOfWeek = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).minusDays(1);
                LocalDateTime endOfWeek = LocalDateTime.now().with(fieldISO, 7).withHour(0).withMinute(0).withSecond(1).plusDays(1);
                if (card.getSize().equals("small")) {
                    value = this.meetingService.getTaskForCard(startOfWeek, endOfWeek, this.user).size();
                } else if (card.getSize().equals("large")) {
                    value = this.meetingService.getTaskForCard(startOfWeek, endOfWeek, this.user);
                }
            }
            break;
            default:
                value = "0";
        }
        return value;
    }

    public String displayMeetingTime(Meeting meeting) {
        String value = "";

        LocalDateTime startDate = DateManager.toLocalDateTime(meeting.getStartDate());
        LocalDateTime endDate = DateManager.toLocalDateTime(meeting.getEndDate());
        if (meeting.isAllDay()) value = startDate.toString();
        else {
            if (!startDate.toLocalDate().isEqual(endDate.toLocalDate())) {
                value = startDate.format(DateManager.dateTimeFormatter) +
                        " → " +
                        endDate.format(DateManager.dateTimeFormatter);
            } else {
                value = startDate.format(DateManager.dateTimeFormatter) +
                        " → " +
                        endDate.toLocalTime().format(DateManager.timeFormatter);
            }
        }
        return value;
    }

    public String displayTaskTime(Meeting meeting) {
        LocalDateTime startDate = DateManager.toLocalDateTime(meeting.getStartDate());

        return startDate.format(DateManager.dateTimeFormatter);
    }

    /**
     * Gets dashboard.
     *
     * @return the dashboard
     */
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * Sets dashboard.
     *
     * @param dashboard the dashboard
     */
    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
