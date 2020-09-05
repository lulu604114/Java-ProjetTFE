package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Meeting;
import com.projet.entities.Patient;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.MeetingService;
import com.projet.utils.DateManager;
import com.projet.utils.Message;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Agenda bean.
 *
 * @author Amaury Lapaque
 */
@Named("agendaBean")
@ViewScoped
public class AgendaBean implements Serializable {
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);
    private FacesMessage PFMessages = null;
    private LazyScheduleModel events;

    private ScheduleEvent<Meeting> event = new DefaultScheduleEvent<Meeting>();

    private boolean slotEventOverlap = true;
    private boolean showWeekNumbers = false;
    private boolean showHeader = true;
    private boolean draggable = true;
    private boolean resizable = true;
    private boolean showWeekends = true;
    private boolean tooltip = true;
    private boolean allDaySlot = true;

    private double aspectRatio = Double.MIN_VALUE;

    private String leftHeaderTemplate = "prev, next, today";
    private String centerHeaderTemplate = "title";
    private String rightHeaderTemplate = "dayGridMonth,timeGridWeek,timeGridDay,listYear";
    private String nextDayThreshold = "09:00:00";
    private String weekNumberCalculation = "local";
    private String weekNumberCalculator = "date.getTime()";
    private String displayEventEnd;
    private String timeFormat;
    private String slotDuration = "00:15:00";
    private String slotLabelInterval;
    private String slotLabelFormat;
    private String scrollTime = "17:30:00";
    private String minTime = "06:00:00";
    private String maxTime = "20:00:00";
    private String locale = "fr";
    private String timeZone = "";
    private String clientTimeZone = "local";
    private String columnHeaderFormat = "timeGridWeek: {weekday: 'short'}";
    private String view = "timeGridWeek";

    private User user;

    private MeetingService meetingService = new MeetingService(Meeting.class);

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.events = new LazyScheduleModel() {
            @Override
            public void loadEvents(LocalDateTime startDate, LocalDateTime endDate) {
                List<Meeting> meetings = meetingService.getMeetings(startDate, endDate, user);
                if (meetings.isEmpty()) return;
                meetings.forEach(meeting -> {
                    addEvent(DefaultScheduleEvent.builder()
                            .title(meeting.getTitle())
                            .startDate(DateManager.toLocalDateTime(meeting.getStartDate()))
                            .endDate(DateManager.toLocalDateTime(meeting.getEndDate()))
                            .description(meeting.getDescription())
                            .styleClass(meeting.getType().toString().toLowerCase())
                            .data(meeting)
                            .allDay(meeting.isAllDay())
                            .overlapAllowed(true)
                            .build()
                    );
                });
            }
        };
    }

    /**
     * Add event.
     */
    public void addEvent() {
        if (event.isAllDay()) {
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }
        this.saveMeeting(event);
        event = new DefaultScheduleEvent<>();
    }

    /**
     * On event select.
     *
     * @param selectEvent the select event
     */
    public void onEventSelect(SelectEvent<ScheduleEvent<Meeting>> selectEvent) {
        event = selectEvent.getObject();
    }

    /**
     * On view change.
     *
     * @param selectEvent the select event
     */
    public void onViewChange(SelectEvent<String> selectEvent) {
        view = selectEvent.getObject();
    }

    /**
     * On date select.
     *
     * @param selectEvent the select event
     */
    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .overlapAllowed(true)
                .data(new Meeting())
                .build();
    }

    /**
     * On event move.
     *
     * @param event the event
     */
    public void onEventMove(ScheduleEntryMoveEvent event) {
        this.saveMeeting(event.getScheduleEvent());
    }

    /**
     * On event resize.
     *
     * @param event the event
     */
    public void onEventResize(ScheduleEntryResizeEvent event) {
        this.saveMeeting(event.getScheduleEvent());
    }

    /**
     * Update meeting.
     *
     * @param event the event
     */
    public void saveMeeting(ScheduleEvent event) {
        MeetingService service = new MeetingService(Meeting.class);
        EntityTransaction transaction = service.getTransaction();
        FacesMessage message;

        transaction.begin();

        try {
            Meeting meeting = service.initMeeting(event);

            service.save(meeting);

            transaction.commit();

            if (meeting.getId() != 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Événement modifié", event.getTitle() + "a été mis à jour");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Événement ", event.getTitle() + "a été ajouté");
            }
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Une erreur est survenue");
            }

            service.close();
        }
        addMessage(message);

    }

    /**
     * On event delete.
     */
    public void onEventDelete() {
        if (this.event != null) {
            MeetingService service = new MeetingService(Meeting.class);
            EntityTransaction transaction = service.getTransaction();

            transaction.begin();

            try {
                Meeting meeting = this.event.getData();
                service.remove(meeting);

                transaction.commit();

                events.deleteEvent(this.event);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Événement ", this.event.getTitle() + "a été supprimé");
                addMessage(message);
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                    message.display(FacesMessage.SEVERITY_ERROR, "Unknown error", "Please retry");
                }
                service.close();
            }
        }
    }

    /**
     * Complete patient.
     *
     * @param query the query
     *
     * @return the list
     */
    public List<Patient> completePatient(String query) {
        User user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        List<Patient> patients = user.getPatients();
        List<Patient> results = new ArrayList<>();
        // check if there are some userAccounts who match with the query
        for (Patient patient : patients) {
            String concatLastFirst = patient.getLastName() + " " + patient.getFirstName();
            String concatFirstLast = patient.getFirstName() + " " + patient.getLastName();
            // check if the firstName or lastName startWith the query received
            if ((patient.getFirstName().toLowerCase().startsWith(query.toLowerCase()) || patient.getLastName().toLowerCase().startsWith(query.toLowerCase())))
                results.add(patient);
            else if (concatFirstLast.toLowerCase().startsWith(query.toLowerCase()) || concatLastFirst.toLowerCase().startsWith(query.toLowerCase()))
                results.add(patient);
        }
        return results;
    }

    /**
     * Display patient label string.
     *
     * @param patient the patient
     *
     * @return the string
     */
    public String displayPatientLabel(Patient patient) {
        String value = "";
        if (patient != null) {
            value = patient.getLastName() + " " + patient.getFirstName();
        }
        return value;
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    /* GETTER && SETTERS FOR THE VIEW */

    /**
     * Gets message.
     *
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     * Gets events.
     *
     * @return the events
     */
    public LazyScheduleModel getEvents() {
        return events;
    }

    /**
     * Sets events.
     *
     * @param events the events
     */
    public void setEvents(LazyScheduleModel events) {
        this.events = events;
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public ScheduleEvent<Meeting> getEvent() {
        return event;
    }

    /**
     * Sets event.
     *
     * @param event the event
     */
    public void setEvent(ScheduleEvent<Meeting> event) {
        this.event = event;
    }

    /**
     * Is slot event overlap boolean.
     *
     * @return the boolean
     */
    public boolean isSlotEventOverlap() {
        return slotEventOverlap;
    }

    /**
     * Sets slot event overlap.
     *
     * @param slotEventOverlap the slot event overlap
     */
    public void setSlotEventOverlap(boolean slotEventOverlap) {
        this.slotEventOverlap = slotEventOverlap;
    }

    /**
     * Is show week numbers boolean.
     *
     * @return the boolean
     */
    public boolean isShowWeekNumbers() {
        return showWeekNumbers;
    }

    /**
     * Sets show week numbers.
     *
     * @param showWeekNumbers the show week numbers
     */
    public void setShowWeekNumbers(boolean showWeekNumbers) {
        this.showWeekNumbers = showWeekNumbers;
    }

    /**
     * Is show header boolean.
     *
     * @return the boolean
     */
    public boolean isShowHeader() {
        return showHeader;
    }

    /**
     * Sets show header.
     *
     * @param showHeader the show header
     */
    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    /**
     * Is draggable boolean.
     *
     * @return the boolean
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Sets draggable.
     *
     * @param draggable the draggable
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * Is resizable boolean.
     *
     * @return the boolean
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * Sets resizable.
     *
     * @param resizable the resizable
     */
    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    /**
     * Is show weekends boolean.
     *
     * @return the boolean
     */
    public boolean isShowWeekends() {
        return showWeekends;
    }

    /**
     * Sets show weekends.
     *
     * @param showWeekends the show weekends
     */
    public void setShowWeekends(boolean showWeekends) {
        this.showWeekends = showWeekends;
    }

    /**
     * Is tooltip boolean.
     *
     * @return the boolean
     */
    public boolean isTooltip() {
        return tooltip;
    }

    /**
     * Sets tooltip.
     *
     * @param tooltip the tooltip
     */
    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Is all day slot boolean.
     *
     * @return the boolean
     */
    public boolean isAllDaySlot() {
        return allDaySlot;
    }

    /**
     * Sets all day slot.
     *
     * @param allDaySlot the all day slot
     */
    public void setAllDaySlot(boolean allDaySlot) {
        this.allDaySlot = allDaySlot;
    }

    /**
     * Gets aspect ratio.
     *
     * @return the aspect ratio
     */
    public double getAspectRatio() {
        return aspectRatio;
    }

    /**
     * Sets aspect ratio.
     *
     * @param aspectRatio the aspect ratio
     */
    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * Gets left header template.
     *
     * @return the left header template
     */
    public String getLeftHeaderTemplate() {
        return leftHeaderTemplate;
    }

    /**
     * Sets left header template.
     *
     * @param leftHeaderTemplate the left header template
     */
    public void setLeftHeaderTemplate(String leftHeaderTemplate) {
        this.leftHeaderTemplate = leftHeaderTemplate;
    }

    /**
     * Gets center header template.
     *
     * @return the center header template
     */
    public String getCenterHeaderTemplate() {
        return centerHeaderTemplate;
    }

    /**
     * Sets center header template.
     *
     * @param centerHeaderTemplate the center header template
     */
    public void setCenterHeaderTemplate(String centerHeaderTemplate) {
        this.centerHeaderTemplate = centerHeaderTemplate;
    }

    /**
     * Gets right header template.
     *
     * @return the right header template
     */
    public String getRightHeaderTemplate() {
        return rightHeaderTemplate;
    }

    /**
     * Sets right header template.
     *
     * @param rightHeaderTemplate the right header template
     */
    public void setRightHeaderTemplate(String rightHeaderTemplate) {
        this.rightHeaderTemplate = rightHeaderTemplate;
    }

    /**
     * Gets next day threshold.
     *
     * @return the next day threshold
     */
    public String getNextDayThreshold() {
        return nextDayThreshold;
    }

    /**
     * Sets next day threshold.
     *
     * @param nextDayThreshold the next day threshold
     */
    public void setNextDayThreshold(String nextDayThreshold) {
        this.nextDayThreshold = nextDayThreshold;
    }

    /**
     * Gets week number calculation.
     *
     * @return the week number calculation
     */
    public String getWeekNumberCalculation() {
        return weekNumberCalculation;
    }

    /**
     * Sets week number calculation.
     *
     * @param weekNumberCalculation the week number calculation
     */
    public void setWeekNumberCalculation(String weekNumberCalculation) {
        this.weekNumberCalculation = weekNumberCalculation;
    }

    /**
     * Gets week number calculator.
     *
     * @return the week number calculator
     */
    public String getWeekNumberCalculator() {
        return weekNumberCalculator;
    }

    /**
     * Sets week number calculator.
     *
     * @param weekNumberCalculator the week number calculator
     */
    public void setWeekNumberCalculator(String weekNumberCalculator) {
        this.weekNumberCalculator = weekNumberCalculator;
    }

    /**
     * Gets display event end.
     *
     * @return the display event end
     */
    public String getDisplayEventEnd() {
        return displayEventEnd;
    }

    /**
     * Sets display event end.
     *
     * @param displayEventEnd the display event end
     */
    public void setDisplayEventEnd(String displayEventEnd) {
        this.displayEventEnd = displayEventEnd;
    }

    /**
     * Gets time format.
     *
     * @return the time format
     */
    public String getTimeFormat() {
        return timeFormat;
    }

    /**
     * Sets time format.
     *
     * @param timeFormat the time format
     */
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    /**
     * Gets slot duration.
     *
     * @return the slot duration
     */
    public String getSlotDuration() {
        return slotDuration;
    }

    /**
     * Sets slot duration.
     *
     * @param slotDuration the slot duration
     */
    public void setSlotDuration(String slotDuration) {
        this.slotDuration = slotDuration;
    }

    /**
     * Gets slot label interval.
     *
     * @return the slot label interval
     */
    public String getSlotLabelInterval() {
        return slotLabelInterval;
    }

    /**
     * Sets slot label interval.
     *
     * @param slotLabelInterval the slot label interval
     */
    public void setSlotLabelInterval(String slotLabelInterval) {
        this.slotLabelInterval = slotLabelInterval;
    }

    /**
     * Gets slot label format.
     *
     * @return the slot label format
     */
    public String getSlotLabelFormat() {
        return slotLabelFormat;
    }

    /**
     * Sets slot label format.
     *
     * @param slotLabelFormat the slot label format
     */
    public void setSlotLabelFormat(String slotLabelFormat) {
        this.slotLabelFormat = slotLabelFormat;
    }

    /**
     * Gets scroll time.
     *
     * @return the scroll time
     */
    public String getScrollTime() {
        return scrollTime;
    }

    /**
     * Sets scroll time.
     *
     * @param scrollTime the scroll time
     */
    public void setScrollTime(String scrollTime) {
        this.scrollTime = scrollTime;
    }

    /**
     * Gets min time.
     *
     * @return the min time
     */
    public String getMinTime() {
        return minTime;
    }

    /**
     * Sets min time.
     *
     * @param minTime the min time
     */
    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    /**
     * Gets max time.
     *
     * @return the max time
     */
    public String getMaxTime() {
        return maxTime;
    }

    /**
     * Sets max time.
     *
     * @param maxTime the max time
     */
    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    /**
     * Gets locale.
     *
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets locale.
     *
     * @param locale the locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * Gets time zone.
     *
     * @return the time zone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets time zone.
     *
     * @param timeZone the time zone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Gets client time zone.
     *
     * @return the client time zone
     */
    public String getClientTimeZone() {
        return clientTimeZone;
    }

    /**
     * Sets client time zone.
     *
     * @param clientTimeZone the client time zone
     */
    public void setClientTimeZone(String clientTimeZone) {
        this.clientTimeZone = clientTimeZone;
    }

    /**
     * Gets column header format.
     *
     * @return the column header format
     */
    public String getColumnHeaderFormat() {
        return columnHeaderFormat;
    }

    /**
     * Sets column header format.
     *
     * @param columnHeaderFormat the column header format
     */
    public void setColumnHeaderFormat(String columnHeaderFormat) {
        this.columnHeaderFormat = columnHeaderFormat;
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    public String getView() {
        return view;
    }

    /**
     * Sets view.
     *
     * @param view the view
     */
    public void setView(String view) {
        this.view = view;
    }

    /**
     * Gets meeting service.
     *
     * @return the meeting service
     */
    public MeetingService getMeetingService() {
        return meetingService;
    }

    /**
     * Sets meeting service.
     *
     * @param meetingService the meeting service
     */
    public void setMeetingService(MeetingService meetingService) {
        this.meetingService = meetingService;
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