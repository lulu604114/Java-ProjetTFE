package com.projet.services;

import com.projet.entities.Meeting;
import com.projet.entities.Patient;
import com.projet.entities.User;
import com.projet.utils.DateManager;
import org.apache.log4j.Logger;
import org.primefaces.model.ScheduleEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The type Meeting service.
 *
 * @author Amaury Lapaque
 */
public class MeetingService extends Service<Meeting> implements Serializable {
    private static final Logger log = Logger.getLogger(MeetingService.class);
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Meeting service.
     *
     */
    public MeetingService() {
        super();
    }

    /**
     * Init meeting meeting.
     *
     * @param event the event
     *
     * @return the meeting
     */
    public Meeting initMeeting(ScheduleEvent<Meeting> event) {
        Meeting meeting = event.getData();

        meeting.setStartDate(DateManager.toCalendar(event.getStartDate()));
        meeting.setEndDate(DateManager.toCalendar(event.getEndDate()));
        meeting.setAllDay(event.isAllDay());
        meeting.setTitle(event.getTitle());

        return meeting;
    }

    @Override
    public Meeting save(Meeting meeting) {
        if (meeting.getId() == 0) {
            em.persist(meeting);
        } else {
            meeting = em.merge(meeting);
        }
        return meeting;
    }

    /**
     * Remove.
     *
     * @param meeting the meeting
     */
    public void remove(Meeting meeting) {
        Meeting meetingDB = em.find(Meeting.class, meeting.getId());
        if (meetingDB != null) {
            em.remove(meetingDB);
        }
    }

    /**
     * Gets meetings.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @param user      the user
     *
     * @return the meetings
     */
    public List<Meeting> getMeetings(LocalDateTime startDate, LocalDateTime endDate, User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("startDate", DateManager.toDate(startDate));
        params.put("endDate", DateManager.toDate(endDate));

        return finder.findByNamedQuery("Meeting.findByUserAndStartDateAndEndDate", params);
    }
    public List<Meeting> getMeetingsByPatient(LocalDateTime startDate, LocalDateTime endDate, User user, Patient patient) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("startDate", DateManager.toDate(startDate));
        params.put("endDate", DateManager.toDate(endDate));
        params.put("patient", patient);
        return finder.findByNamedQuery("Meeting.findByUserAndStartDateAndEndDateAndPatient", params);
    }

    /**
     * Gets meetings for card.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @param user      the user
     *
     * @return the meetings for card
     */
    public List<Meeting> getMeetingsForCard(LocalDateTime startDate, LocalDateTime endDate, User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("startDate", DateManager.toDate(startDate));
        params.put("endDate", DateManager.toDate(endDate));

        return finder.findByNamedQuery("Meeting.findMeetingByUserAndStartDateAndEndDate", params);
    }

    /**
     * Gets session for card.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @param user      the user
     *
     * @return the session for card
     */
    public List<Meeting> getSessionForCard(LocalDateTime startDate, LocalDateTime endDate, User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("startDate", DateManager.toDate(startDate));
        params.put("endDate", DateManager.toDate(endDate));

        return finder.findByNamedQuery("Meeting.findSessionByUserAndStartDateAndEndDate", params);
    }

    /**
     * Gets task for card.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @param user      the user
     *
     * @return the task for card
     */
    public List<Meeting> getTaskForCard(LocalDateTime startDate, LocalDateTime endDate, User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("startDate", DateManager.toDate(startDate));
        params.put("endDate", DateManager.toDate(endDate));

        return finder.findByNamedQuery("Meeting.findTaskByUserAndStartDateAndEndDate", params);
    }
}
