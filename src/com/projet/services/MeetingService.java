package com.projet.services;

import com.projet.conf.App;
import com.projet.entities.User;
import org.apache.log4j.Logger;
import com.projet.security.SecurityManager;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * The type Meeting service.
 *
 * @author Amaury Lapaque
 */
public class MeetingService extends Service implements Serializable {
    private static final Logger log = Logger.getLogger(MeetingService.class);
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Meeting service.
     *
     * @param ec the ec
     */
    public MeetingService(Class<?> ec ) {
        super(ec);
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    /**
     * Gets meetings.
     *
     * @return the meetings
     */
    public List getMeetings() {
        Map<String, Integer> params = new HashMap<>();
        return finder.findByNamedQuery("Meeting.findAll", null);
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
}
