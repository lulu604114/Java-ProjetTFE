package com.projet.controllers;

import com.projet.conf.Config;
import com.projet.controllers.utils.Message;
import com.projet.entities.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 09/03/2020
 * Time: 13:10
 * =================================================================
 */
@Named("profileBean")
@SessionScoped
public class ProfileBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Message message = Message.getMessage(Config.BUNDLE_MESSAGE);

    private User user;
    private boolean editable = true;

    @PostConstruct
    public void init() {
        Subject currentUser = SecurityUtils.getSubject();
        user = (User) currentUser.getSession().getAttribute(Config.SESSION_USER);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}