package com.projet.controllers;

import com.projet.conf.App;
import com.projet.utility.Message;
import com.projet.entities.User;
import com.projet.services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 09/08/2020
 * Time: 21:11
 * =================================================================
 */
@Named("userRegistration")
@RequestScoped
public class UserRegistration implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private User user;

    @PostConstruct
    public void init() {
        this.user = new User();
    }

    public String registrate() {
        UserService service = new UserService(User.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            User user = service.createUser(this.user);

            service.save(user);

            transaction.commit();

            return "/successRegistration.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();

                message.display(FacesMessage.SEVERITY_ERROR, "Unknown error");

                return "";
            }

            service.close();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}