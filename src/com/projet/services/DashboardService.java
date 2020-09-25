package com.projet.services;

import com.projet.entities.Dashboard;
import com.projet.entities.User;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DashboardService extends Service<Dashboard> implements Serializable {
    private static final Logger log = Logger.getLogger(DashboardService.class);
    private static final long serialVersionUID = 1L;
    Map<String, Object> params = new HashMap<String, Object>();

    public DashboardService() {
        super();
    }

    public Dashboard getDashboard(User user) {
        params.put("user", user);
        return finder.findByNamedQuery("Dashboard.findDashboardByUser", params).get(0);
    }

    @Override
    public Dashboard save(Dashboard dashboard) {
        return null;
    }
}
