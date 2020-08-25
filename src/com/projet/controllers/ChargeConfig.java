package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.FinancialAccount;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.UserService;

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

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {

            if (addFinancialAccount)
                service.addUserFinancialAccount(user);

            if (addSupplier)
                service.addUserSupplier(user);

            service.addUserDiary(user);

            user.setChargeConfigSet(true);

            service.save(user);

            transaction.commit();
        } finally {
            if(transaction.isActive())
                transaction.rollback();

            service.close();
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