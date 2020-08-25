package com.projet.controllers;

import com.projet.entities.Charge;

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
 * Date: 21/08/2020
 * Time: 22:59
 * =================================================================
 */
@Named("chargeDetailView")
@SessionScoped
public class ChargeDetailView implements Serializable {
    private static final long serialVersionUID = 1L;

    private Charge charge;

    @PostConstruct
    public void init() {

    }

    public String edit(Charge charge) {
        this.charge = charge;

        return "chargeDetail.xhtml?faces-redirect=true";
    }



    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }
}