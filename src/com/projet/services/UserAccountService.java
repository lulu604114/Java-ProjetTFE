package com.projet.services;

import com.projet.entities.FinancialAccount;
import com.projet.entities.User;
import com.projet.entities.UserAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 28/08/2020
 * Time: 23:27
 * =================================================================
 */
public class UserAccountService extends Service<UserAccount> {

    public UserAccountService(Class<?> ec) {
        super(ec);
    }

    public List<UserAccount> getByUser(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);

        return finder.findByNamedQuery("UA.findByUser", param);
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        if (userAccount.getId() == 0) {
            em.persist(userAccount);
        } else {
            userAccount = em.merge(userAccount);
        }

        return userAccount;
    }

    public List<UserAccount> createDefaultUserAccount(User user) {
        FinancialAccountService service = new FinancialAccountService(FinancialAccount.class);

        List<FinancialAccount> financialAccounts = service.getDefaultFinancialAccount();
        List<UserAccount> userAccounts = new ArrayList<>();

        for (FinancialAccount account: financialAccounts) {
            UserAccount userAccount = new UserAccount();
            userAccount.setFinancialAccount(account);
            userAccount.setUser(user);
            userAccount.setPrivatePart(account.getDefaultPrivatePart());
            userAccount.setTaxDeductible(account.getDefaultTaxDeductible());
            userAccounts.add(userAccount);
        }

        return userAccounts;
    }


}
