package com.projet.services;

import com.projet.conf.App;
import com.projet.entities.User;
import org.apache.log4j.Logger;
import com.projet.security.SecurityManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendaService extends Service implements Serializable {
    private static final Logger log = Logger.getLogger(AgendaService.class);
    private static final long serialVersionUID = 1L;

    public AgendaService(Class<?> ec ) {
        super(ec);
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    public List getEvents() {
        Map<String, Integer> params = new HashMap<>();
        User sessionUser = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        params.put("id", sessionUser.getId());

        return finder.findByNamedQuery("Events.findByUser", params);
    }
}
