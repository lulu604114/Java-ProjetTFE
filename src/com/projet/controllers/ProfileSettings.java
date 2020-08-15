package com.projet.controllers;

import com.projet.conf.App;
import com.projet.connection.EMF;
import com.projet.controllers.utils.Message;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.rmi.NoSuchObjectException;


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
@Named("profileSettings")
@SessionScoped
public class ProfileSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private User user;
    private User editedUser;

    @PostConstruct
    public void init() {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        edit();
    }

    public void save() {
        UserService service = new UserService(User.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            user.setFields(editedUser);

            service.save(user);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Modifications réussies");
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();

                message.display(FacesMessage.SEVERITY_ERROR, "Unknown error");
            }

            service.close();
        }
    }

    public void edit() {
        this.editedUser = user.clone();
    }

    public void cancel() {
        message.display(FacesMessage.SEVERITY_WARN, "Annulation", "Aucunes modifications réalisées");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getEditedUser() {
        return editedUser;
    }

    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
    }
}