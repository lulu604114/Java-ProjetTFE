package com.projet.services;

import com.projet.entities.AccountCategory;
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
 * Date: 01/09/2020
 * Time: 16:43
 * =================================================================
 */
public class AccountCategoryService extends Service<AccountCategory>{


    public AccountCategoryService(Class<?> ec) {
        super(ec);
    }

    public List<AccountCategory> getUserAccountCategory(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);

        return finder.findByNamedQuery("AC.findByUser", param);
    }

    public List<AccountCategory> getAccountCategorybyDefault() {
        return finder.findByNamedQuery("AC.findDefault", null);
    }

    @Override
    public AccountCategory save(AccountCategory accountCategory) {
        if (accountCategory.getId() == 0) {
            em.persist(accountCategory);
        } else {
            accountCategory = em.merge(accountCategory);
        }

        return accountCategory;
    }
}
