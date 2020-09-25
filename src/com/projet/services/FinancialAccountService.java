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


    public FinancialAccountService() {
        super();
    }

    public List<FinancialAccount> findByLabelOrCode(User user, String label, String label2) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("label", label);
        param.put("label2", label2);

        return finder.findByNamedQuery("FA.findByLabelOrCode", param);
    }

    public List<FinancialAccount> getDefaultFinancialAccount() {
        Map<String, Boolean> param = new HashMap<>();
        param.put("boolean", true);

        return finder.findByNamedQuery("FA.findDefaultFa", param);
    }

    @Override
    public FinancialAccount save(FinancialAccount financialAccount) {
        return null;
    }
}