package com.projet.services;

import com.projet.entities.AccountCategory;
import com.projet.entities.FinancialAccount;
import com.projet.entities.User;
import com.projet.entities.UserAccount;

import javax.persistence.Query;
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

    public UserAccountService() {
        super();
    }

    public List<UserAccount> getByUser(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);

        return finder.findByNamedQuery("UA.findByUser", param);
    }

    public List<UserAccount> getByUserAndCategory(User user, AccountCategory category) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("accountCategory", category);

        return finder.findByNamedQuery("UA.findByCategory", param);
    }

    public int deleteById(User user, List<AccountCategory> accountCategories) {
        Query query = em.createNamedQuery("UA.deleteById");
        query.setParameter("user", user);
        query.setParameter("accountCategory", accountCategories);

        return query.executeUpdate();
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

    public void delete(UserAccount userAccount) {

        FinancialAccount financialAccount = em.find(FinancialAccount.class, userAccount.getFinancialAccount().getId());

        delete(userAccount.getId());

        if (! financialAccount.isDefaultFa())
            em.remove(financialAccount);
    }

    public List<UserAccount> createDefaultUserAccount(User user) {
        FinancialAccountService service = new FinancialAccountService();

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
