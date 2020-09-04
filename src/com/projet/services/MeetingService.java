package com.projet.services;

import com.projet.conf.App;
import com.projet.entities.Meeting;
import com.projet.entities.User;
import org.apache.log4j.Logger;
import com.projet.security.SecurityManager;
import org.primefaces.model.ScheduleEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
     * @param ec the ec
     */
    public MeetingService(Class<?> ec) {
        super(ec);
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

        meeting.setStartDate(this.toCalendar(event.getStartDate()));
        meeting.setEndDate(this.toCalendar(event.getEndDate()));
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
        params.put("user", (User) user);
        params.put("startDate", (Date) this.toDate(startDate));
        params.put("endDate", (Date) this.toDate(endDate));

        return finder.findByNamedQuery("Meeting.findByUserAndStartDateAndEndDate", params);
    }

    /**
     * To date date.
     *
     * @param localDateTime the local date time
     *
     * @return the date
     */
    public Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * To local date time local date time.
     *
     * @param calendar the calendar
     *
     * @return the local date time
     */
    public LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    /**
     * To calendar calendar.
     *
     * @param localDateTime the local date time
     *
     * @return the calendar
     */
    public Calendar toCalendar(LocalDateTime localDateTime) {
        Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }
}
