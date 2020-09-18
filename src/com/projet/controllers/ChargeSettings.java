package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.*;
import com.projet.utils.Message;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionListener;
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
@ViewScoped
public class ChargeSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private User user;

    private List<AccountCategory> accountCategories;
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

        FinancialYearService financialYearService = new FinancialYearService(FinancialYear.class);
        this.financialYears = financialYearService.getFinancialYearByUser(user);

        this.accountCategory = new AccountCategory();
        this.diary = new Diary();
        this.userAccount = new UserAccount();
        this.userAccount.setFinancialAccount(new FinancialAccount());
    }

    public List<AccountCategory> getUserAccountCategories() {
        AccountCategoryService service = new AccountCategoryService(AccountCategory.class);

        return service.getUserAccountCategory(user);
    }

    public void editCategory(AccountCategory category) {
        this.accountCategory = category;
    }

    public void editDiary(Diary diary) {
        this.diary = diary;
    }

    public void editUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void createCategory() {
        this.accountCategory = new AccountCategory();
    }

    public void createDiary() {
        this.diary = new Diary();
    }

    public void createUserAccount() {
        this.userAccount = new UserAccount();
        this.userAccount.setFinancialAccount(new FinancialAccount());
    }

    private List<AccountCategory> map_userAccountList_by_account_category() {
        UserAccountService service = new UserAccountService(UserAccount.class);

        AccountCategoryService accountCategoryService = new AccountCategoryService(AccountCategory.class);

        List<UserAccount> userAccounts = service.getByUser(user);
        Collections.sort(userAccounts);

        List<AccountCategory> accountCategories = accountCategoryService.getAccountCategoryByDefault(user);
        List<AccountCategory> userAccountCategories = accountCategoryService.getUserAccountCategory(user);

        if (userAccountCategories != null && ! userAccountCategories.isEmpty())
            accountCategories.addAll(userAccountCategories);

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

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "Catégorie " + accountCategory.getLabel() + " ajoutée");

            this.accountCategories = map_userAccountList_by_account_category();
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void updateAccountCategory() {
        AccountCategoryService service = new AccountCategoryService(AccountCategory.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {

            service.save(accountCategory);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "Catégorie " + accountCategory.getLabel() + " Modifiée");

            this.accountCategories = map_userAccountList_by_account_category();
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void deleteAccountCategory(AccountCategory accountCategory) {
        AccountCategoryService accountCategoryService = new AccountCategoryService(AccountCategory.class);
        UserAccountService service = new UserAccountService(UserAccount.class);
        accountCategoryService.setEm(service.getEm());

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {

            List<UserAccount> userAccounts = service.getByUserAndCategory(user, accountCategory);

            if (userAccounts != null && !userAccounts.isEmpty()) {
                for (UserAccount account : userAccounts)
                    service.delete(account.getId());
            }

            accountCategoryService.delete(accountCategory.getId());

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "suppression reussie");

            this.accountCategories = map_userAccountList_by_account_category();

        } catch(Exception e) {
            message.display(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible de supprimer cette catégorie.");

            transaction.rollback();

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

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "charge " + userAccount.getFinancialAccount().getLabel() + " ajoutée");

            this.accountCategories = map_userAccountList_by_account_category();

        } finally {
            if(transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void updateUserAccount() {
        UserAccountService service = new UserAccountService(UserAccount.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            service.save(userAccount);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "charge " + userAccount.getFinancialAccount().getLabel() + " ajoutée");

            this.accountCategories = map_userAccountList_by_account_category();

        } finally {
            if(transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void deleteUserAccount(UserAccount userAccount) {
        UserAccountService service = new UserAccountService(UserAccount.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            service.delete(userAccount.getId());

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "charge " + userAccount.getFinancialAccount().getLabel() + " supprimée");

            this.accountCategories = map_userAccountList_by_account_category();

        } catch(Exception e) {
            message.display(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible de supprimer cette charge.");

            transaction.rollback();

        } finally {
            if(transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void addDiary() {
        DiaryService service = new DiaryService(Diary.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            user.addDiary(diary);

            service.save(diary);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "journal " + diary.getLabel() + " ajouté");

        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void updateDiary() {
        DiaryService service = new DiaryService(Diary.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            service.save(diary);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "journal " + diary.getLabel() + " modifié");

        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }
}