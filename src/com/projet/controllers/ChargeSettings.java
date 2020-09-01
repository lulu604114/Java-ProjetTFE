package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.AccountCategoryService;
import com.projet.services.UserAccountService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 31/08/2020
 * Time: 17:09
 * =================================================================
 */
@Named("chargeSettings")
@ViewScoped
public class ChargeSettings implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;

    List<AccountCategory> accountCategories;
    private List<Diary> diaries;
    private List<FinancialYear> financialYears;

    @PostConstruct
    public void init() {
        UserAccountService service = new UserAccountService(UserAccount.class);
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.accountCategories = map_userAccountList_by_account_category(service.getByUser(user));
        this.diaries = user.getDiaries();
        this.financialYears = user.getFinancialYears();
    }

    public List<AccountCategory> map_userAccountList_by_account_category(List<UserAccount> userAccounts) {
        Collections.sort(userAccounts);
        AccountCategoryService accountCategoryService = new AccountCategoryService(AccountCategory.class);

        List<AccountCategory> accountCategories = accountCategoryService.getUserAccountCategory(user);
        accountCategories.addAll(accountCategoryService.getAccountCategorybyDefault());

        for (AccountCategory accountCategory:accountCategories) {
            List<UserAccount> results = new ArrayList<>();
            for (UserAccount userAccount:userAccounts) {
                if (userAccount.getFinancialAccount().getAccountCategory().getLabel().equals(accountCategory.getLabel())) {
                    results.add(userAccount);
                }
            }
            accountCategory.setUserAccounts(results);
        }

        return accountCategories;
    }

    public List<AccountCategory> getAccountCategories() {
        return accountCategories;
    }

    public void setAccountCategories(List<AccountCategory> accountCategories) {
        this.accountCategories = accountCategories;
    }

    public List<Diary> getDiaries() {
        return diaries;
    }

    public void setDiaries(List<Diary> diaries) {
        this.diaries = diaries;
    }

    public List<FinancialYear> getFinancialYears() {
        return financialYears;
    }

    public void setFinancialYears(List<FinancialYear> financialYears) {
        this.financialYears = financialYears;
    }
}