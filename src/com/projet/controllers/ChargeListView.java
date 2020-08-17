package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Charge;
import com.projet.entities.User;
import com.projet.security.SecurityManager;

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
 * Date: 13/08/2020
 * Time: 20:29
 * =================================================================
 */
@Named("chargeListView")
@SessionScoped
public class ChargeListView implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Charge> chargeList;

    @PostConstruct
    public void init() {
        User user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.chargeList = user.getCharges();
    }

    public List<Charge> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<Charge> chargeList) {
        this.chargeList = chargeList;
    }
}
