package com.projet.services;

import com.projet.entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 30/08/2020
 * Time: 10:30
 * =================================================================
 */
public class AccountItemService extends Service<AccountItem>{

    public AccountItemService() {
        super();
    }

    public List<AccountItem> getByUserAccountAndFinancialYear(UserAccount userAccount, FinancialYear financialYear) {
        Map<String, Object> param = new HashMap<>();
        param.put("userAccount", userAccount);
        param.put("financialYear", financialYear);

        return finder.findByNamedQuery("AI.findByUserAccountAndFinancialYear", param);
    }

    @Override
    public AccountItem save(AccountItem accountItem) {
        if (accountItem.getId() == 0) {
            em.persist(accountItem);
        } else {
            accountItem = em.merge(accountItem);
        }

        return accountItem;
    }

    public AccountItem finalizeAccountItem(Charge charge, AccountItem accountItem) {
        accountItem.setFinancialYear(em.merge(charge.getFinancialYear()));

        return accountItem;
    }


    /**
     * Calculate the tax deductible amount of an accountItem.
     * @param accountItem AccountItem who need to be calculate
     * @return the deductible amount
     */
    public double calculate_deductible_amount_of_accountItem(AccountItem accountItem) {
        double amount = accountItem.getAmount();
        double privatePart = accountItem.getPrivatePart();
        double deductible = accountItem.getTaxDeductible();
        double result = 0;

        result = ((amount - ((amount / 100) * privatePart)) / 100) * deductible;

        // if accountItem have a parent the parent became the base of the calcul
        if (accountItem.getParent() != null) {
            accountItem = accountItem.getParent();
        }

        // if accountItem is redeemable the deductible amount is devided by the number of accountItem
        if (accountItem.getAccountItems() != null && accountItem.getAccountItems().size() != 0)
            return result / (accountItem.getAccountItems().size() + 1);
        else
            return result;
    }

    /**
     * Calculate the sum of each tax deductible amount from a list of accountItem
     * @param accountItems List of accountItem
     * @return the sum of each deductible amount
     */
    public double calculate_deductible_amount_of_accountItem_list(List<AccountItem> accountItems) {
        double total = 0;

        for (AccountItem item : accountItems) {

            total += calculate_deductible_amount_of_accountItem(item);
        }

        return total;
    }

    /**
     * Calculate the sum of each accountItem that has been imputed
     * @param accountItems
     * @return
     */
    public double calculate_imputed_amount(List<AccountItem> accountItems) {
        double total = 0;

        for (AccountItem item : accountItems) {
            if (item.getParent() != null)
                continue;

            total += item.getAmount();
        }

        return total;
    }

    public AccountItem create_redeemable_accountItem(AccountItem accountItem, int redeemableDuration, User user) throws CloneNotSupportedException {
        FinancialYearService service = new FinancialYearService();

        int parentAccountItemfinancialYear = accountItem.getFinancialYear().getBeginAt();

        for (int i = 1; i < redeemableDuration; i++) {
            AccountItem accountItemClone = accountItem.clone();

            FinancialYear financialYear = service.getUserFinancialYearByDate(user, parentAccountItemfinancialYear + i);

            if (financialYear == null) {
                financialYear = service.createFinancialYear(parentAccountItemfinancialYear + i, user);
            } else {
                financialYear = em.merge(financialYear);
            }

            accountItemClone.setFinancialYear(financialYear);

            accountItem.getCharge().addAccountItem(accountItem.addAccountItem(accountItemClone));
        }

        return accountItem;
    }
}
