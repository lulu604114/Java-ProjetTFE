package com.projet.controllers;

import com.projet.entities.Meeting;
import com.projet.services.MeetingService;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Agenda bean.
 *
 * @author Amaury Lapaque
 */
@Named("agendaBean")
@ViewScoped
public class AgendaBean implements Serializable {

    private ScheduleModel eventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

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
    private String slotDuration = "00:30:00";
    private String slotLabelInterval;
    private String slotLabelFormat;
    private String scrollTime = "06:00:00";
    private String minTime = "08:00:00";
    private String maxTime = "17:30:00";
    private String locale = "fr";
    private String timeZone = "";
    private String clientTimeZone = "local";
    private String columnHeaderFormat = "";
    private String view = "timeGridWeek";

    private MeetingService meetingService = new MeetingService(Meeting.class);

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
//        eventModel = new DefaultScheduleModel();
//        List<Meeting> meetings = meetingService.getMeetings();
//        meetings.forEach(meeting -> {
//            DefaultScheduleEvent event = DefaultScheduleEvent.builder()
//                                        .title(meeting.getTitle())
//                                        .startDate(meeting.getStartDate())
//                                        .endDate(meeting.getEndDate())
//                                        .description(meeting.getDescription())
//                                        .data(meeting)
//                                        .overlapAllowed(true)
//                                        .build();
//            eventModel.addEvent(event);
//        });
    }

    /**
     * Gets random date time.
     *
     * @param base the base
     *
     * @return the random date time
     */
    public LocalDateTime getRandomDateTime(LocalDateTime base) {
        LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
        return dateTime.plusDays(((int) (Math.random() * 30)));
    }

    /**
     * Gets event model.
     *
     * @return the event model
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    private LocalDateTime previousDay8Pm() {
        return LocalDateTime.now().minusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime previousDay11Pm() {
        return LocalDateTime.now().minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today1Pm() {
        return LocalDateTime.now().withHour(13).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime theDayAfter3Pm() {
        return LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today6Pm() {
        return LocalDateTime.now().withHour(18).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay9Am() {
        return LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay11Am() {
        return LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime fourDaysLater3pm() {
        return LocalDateTime.now().plusDays(4).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime sevenDaysLater0am() {
        return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime eightDaysLater0am() {
        return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * Gets initial date.
     *
     * @return the initial date
     */
    public LocalDate getInitialDate() {
        return LocalDate.now().plusDays(1);
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public ScheduleEvent<?> getEvent() {
        return event;
    }

    /**
     * Sets event.
     *
     * @param event the event
     */
    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    /**
     * Add event.
     */
    public void addEvent() {
        if (event.isAllDay()) {
            // see https://github.com/primefaces/primefaces/issues/1164
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);

        event = new DefaultScheduleEvent<>();
    }

    /**
     * On event select.
     *
     * @param selectEvent the select event
     */
    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
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
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
    }

    /**
     * On event move.
     *
     * @param event the event
     */
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event.getDeltaAsDuration());

        addMessage(message);
    }

    /**
     * On event resize.
     *
     * @param event the event
     */
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

        addMessage(message);
    }

    /**
     * On event delete.
     */
    public void onEventDelete() {
        String eventId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventId");
        if (event != null) {
            ScheduleEvent<?> event = eventModel.getEvent(eventId);
            eventModel.deleteEvent(event);
        }
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
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
        return aspectRatio == 0 ? Double.MIN_VALUE : aspectRatio;
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
}