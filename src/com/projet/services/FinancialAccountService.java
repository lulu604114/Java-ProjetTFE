package com.projet.services;

import com.projet.entities.FinancialAccount;
import com.projet.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 26/08/2020
 * Time: 23:25
 * =================================================================
 */
public class FinancialAccountService extends Service<FinancialAccount>{


    public FinancialAccountService(Class<?> ec) {
        super(ec);
    }

    public List<FinancialAccount> findByLabelOrCode(User user, String label) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("label", label);

        return finder.findByNamedQuery("FA.findByLabelOrCode", param);
    }

    @Override
    public FinancialAccount save(FinancialAccount financialAccount) {
        return null;
    }
}