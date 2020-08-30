package com.projet.services;

import com.projet.entities.AccountItem;
import com.projet.entities.Charge;

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


}
