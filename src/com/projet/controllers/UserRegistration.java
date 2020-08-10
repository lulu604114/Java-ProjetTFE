package com.projet.controllers;

import com.projet.entities.User;
import com.projet.services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
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

    private User user;

    @PostConstruct
    public void init() {
        this.user = new User();
    }

    public void registrate() {
        /* UserService service = new UserService(User.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            service.save(user);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            service.close();
        }*/
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}