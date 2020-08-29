package com.projet.services;

import com.projet.conf.App;
import com.projet.entities.User;
import org.apache.log4j.Logger;
import com.projet.security.SecurityManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        User sessionUser = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        params.put("id", sessionUser.getId());

        return finder.findByNamedQuery("Meeting.findAll", params);
    }
}
