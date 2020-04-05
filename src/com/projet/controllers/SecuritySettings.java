package com.projet.controllers;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 03/04/2020
 * Time: 18:40
 * =================================================================
 */
@Named("securitySettings")
@SessionScoped
public class SecuritySettings implements Serializable {

    private static final long serialVersionUID = 1L;

    public SecuritySettings() {
    }

}