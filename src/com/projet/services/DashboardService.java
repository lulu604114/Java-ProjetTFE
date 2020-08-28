package com.projet.services;

import com.projet.connection.EMF;
import com.projet.dao.EntityFinder;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.Dashboard;
import com.projet.entities.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardService extends Service<Dashboard> implements Serializable {
    private static final Logger log = Logger.getLogger(DashboardService.class);
    private static final long serialVersionUID = 1L;
    Map<String, Object> params = new HashMap<String, Object>();

    public DashboardService(Class<?> ec) {
        super(ec);
    }

    public Dashboard getDashboard(User user) {
        params.put("user", user);
        return finder.findByNamedQuery("Dashboard.findDashboardByUser", params).get(0);
    }

    @Override
    public Dashboard save(Dashboard dashboard) {
        return null;
    }

    public void initializeDashboardCard(Dashboard dashboard) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("initializeDashboard");
        query.setParameter("element", dashboard.getId());
        query.execute();
    }
}
