package com.projet.services;

import com.projet.entities.FinancialYear;
import com.projet.entities.User;

import java.time.Year;
import java.util.*;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 25/08/2020
 * Time: 14:22
 * =================================================================
 */
public class FinancialYearService extends Service<FinancialYear> {

    public FinancialYearService(Class<?> ec) {
        super(ec);
    }

    public FinancialYear getUserFinancialYearByDate(User user, int year) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("year", year);

        return finder.findOneByNamedQuery("FY.FindByUserAndYear", param);
    }

    @Override
    public FinancialYear save(FinancialYear financialYear) {
        if (financialYear.getId() == 0) {
            em.persist(financialYear);
        } else {
            financialYear = em.merge(financialYear);
        }

        return financialYear;
    }

    public FinancialYear createFinancialYear(int year, User user) {

        FinancialYear financialYear = new FinancialYear();
        financialYear.setBeginAt(year);
        return user.addFinancialYear(financialYear);
    }
}
