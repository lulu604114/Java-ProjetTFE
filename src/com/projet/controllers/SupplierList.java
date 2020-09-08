package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.User;
import com.projet.entities.UserSupplier;
import com.projet.security.SecurityManager;
import com.projet.services.UserSupplierService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 01/09/2020
 * Time: 21:24
 * =================================================================
 */
@Named("supplierListView")
@SessionScoped
public class SupplierList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<UserSupplier> userSuppliers;
    private User user;

    @PostConstruct
    public void init() {
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.userSuppliers = user.getUserSuppliers();

    }

    public List<UserSupplier> getUserSuppliers() {
        return userSuppliers;
    }

    public void setUserSuppliers(List<UserSupplier> userSuppliers) {
        this.userSuppliers = userSuppliers;
    }
}