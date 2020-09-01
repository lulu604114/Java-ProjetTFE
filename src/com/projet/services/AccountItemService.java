package com.projet.services;

import com.projet.entities.AccountItem;
import com.projet.entities.Charge;

import java.util.List;

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

    public AccountItemService(Class<?> ec) {
        super(ec);
    }

    @Override
    public AccountItem save(AccountItem accountItem) {
        return null;
    }

    public AccountItem finalizeAccountItem(Charge charge, AccountItem accountItem) {
        accountItem.setFinancialYear(charge.getFinancialYear());

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

        return ((amount - ((amount / 100) * privatePart)) / 100) * deductible;
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
            total += item.getAmount();
        }

        return total;
    }


}
