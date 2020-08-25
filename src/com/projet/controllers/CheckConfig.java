package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.User;
import com.projet.security.SecurityManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 22/08/2020
 * Time: 11:41
 * =================================================================
 */
@Named("checkConfig")
@SessionScoped
public class CheckConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;

    @PostConstruct
    public void init() {
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
    }

    public String userChargeIsSet() {
        if (user.isChargeConfigSet()) {
            return "/app/charge/chargeList";
        }

        return "/app/charge/chargeConfig";
    }

}