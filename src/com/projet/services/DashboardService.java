package com.projet.services;

import com.projet.entities.Dashboard;
import com.projet.entities.User;
import org.apache.log4j.Logger;

import java.io.Serializable;

public class DashboardService extends Service<Dashboard> implements Serializable {
    private static final Logger log = Logger.getLogger(DashboardService.class);
    private static final long serialVersionUID = 1L;

    public DashboardService(Class<?> ec) {
        super(ec);
    }

    public Dashboard getDashboard(User user) {
        return user.getDashboards().get(0);
    }

    @Override
    public Dashboard save(Dashboard dashboard) {
        return null;
    }
}
