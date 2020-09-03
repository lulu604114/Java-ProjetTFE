package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.AccountCategoryService;
import com.projet.services.DiaryService;
import com.projet.services.UserAccountService;
import com.projet.services.UserService;
import com.projet.utils.Message;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
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
@RequestScoped
public class ChargeSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private User user;

    List<AccountCategory> accountCategories;
    private List<Diary> diaries;
    private List<FinancialYear> financialYears;

    private AccountCategory accountCategory;
    private UserAccount userAccount;
    private Diary diary;

    @PostConstruct
    public void init() {
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.accountCategories = map_userAccountList_by_account_category();
        this.diaries = user.getDiaries();
        this.financialYears = user.getFinancialYears();

        this.accountCategory = new AccountCategory();
        this.diary = new Diary();
        this.userAccount = new UserAccount();
        this.userAccount.setFinancialAccount(new FinancialAccount());
    }

    public List<AccountCategory> map_userAccountList_by_account_category() {
        UserAccountService service = new UserAccountService(UserAccount.class);

        AccountCategoryService accountCategoryService = new AccountCategoryService(AccountCategory.class);

        List<UserAccount> userAccounts = service.getByUser(user);
        Collections.sort(userAccounts);

        List<AccountCategory> accountCategories = accountCategoryService.getUserAccountCategory(user);
        accountCategories.addAll(accountCategoryService.getAccountCategorybyDefault());
        Collections.sort(accountCategories);

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

    public AccountCategory getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(AccountCategory accountCategory) {
        this.accountCategory = accountCategory;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public void addAccountCategory() {
        AccountCategoryService service = new AccountCategoryService(AccountCategory.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            accountCategory.setUser(user);

            service.save(accountCategory);

            accountCategories.add(accountCategory);

            map_userAccountList_by_account_category();

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "Catégorie " + accountCategory.getLabel() + " ajoutée");

        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void addUserAccount() {
        UserAccountService service = new UserAccountService(UserAccount.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            userAccount.setUser(user);

            service.save(userAccount);

            map_userAccountList_by_account_category();

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "charge " + userAccount.getFinancialAccount().getLabel() + " ajoutée");
        } finally {
            if(transaction.isActive())
                transaction.rollback();

            service.close();
        }

    }

    public void addDiary() {
        UserService service = new UserService(User.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            user.addDiary(diary);

            service.save(user);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "journal " + diary.getLabel() + " ajouté");

        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }
}