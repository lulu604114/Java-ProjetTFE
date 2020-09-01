package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.DiaryService;
import com.projet.services.UserAccountService;
import com.projet.services.UserService;
import com.projet.services.UserSupplierService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 24/08/2020
 * Time: 22:14
 * =================================================================
 */
@Named("chargeConfig")
@SessionScoped
public class ChargeConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;

    private boolean addFinancialAccount;
    private boolean addSupplier;

    @PostConstruct
    public void init() {
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
    }

    public String setConfig() {
        UserService service = new UserService(User.class);
        DiaryService diaryService = new DiaryService(Diary.class);
        UserAccountService userAccountService = new UserAccountService(UserAccount.class);
        UserSupplierService userSupplierService = new UserSupplierService(UserSupplier.class);



        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {

            if (addFinancialAccount) {
                List<UserAccount> userAccounts = userAccountService.createDefaultUserAccount(this.user);

                user.setUserAccounts(userAccounts);
            }

            if (addSupplier) {
                List<UserSupplier> userSuppliers = userSupplierService.createDefaultSupplier(this.user);

                user.setUserSuppliers(userSuppliers);
            }

            Diary diary = diaryService.createDefaultDiary();

            user.addDiary(diary);

            user.setChargeConfigSet(true);

            service.save(user);

            transaction.commit();
        } finally {
            if(transaction.isActive())
                transaction.rollback();

            service.close();
            diaryService.close();
            userAccountService.close();
            userSupplierService.close();

        }

        return "/app/charge/chargeList?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAddFinancialAccount() {
        return addFinancialAccount;
    }

    public void setAddFinancialAccount(boolean addFinancialAccount) {
        this.addFinancialAccount = addFinancialAccount;
    }

    public boolean isAddSupplier() {
        return addSupplier;
    }

    public void setAddSupplier(boolean addSupplier) {
        this.addSupplier = addSupplier;
    }
}