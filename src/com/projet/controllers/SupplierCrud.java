package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Supplier;
import com.projet.entities.User;
import com.projet.entities.UserSupplier;
import com.projet.enumeration.ChargeStatus;
import com.projet.security.SecurityManager;
import com.projet.services.UserService;
import com.projet.services.UserSupplierService;
import com.projet.utils.Message;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
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
 * Date: 02/09/2020
 * Time: 22:31
 * =================================================================
 */
@Named("supplierCrud")
@ViewScoped
public class SupplierCrud implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private UserSupplier userSupplier;
    private User user;

    @PostConstruct
    public void init() {
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
    }

    public void create_new_supplier() {
        this.userSupplier = new UserSupplier();
        this.userSupplier.setSupplier(new Supplier());
    }

    public void editSupplier(UserSupplier userSupplier) {
        this.userSupplier = userSupplier;
    }


    public void createSupplier() {
        UserSupplierService service = new UserSupplierService(UserSupplier.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            userSupplier.getSupplier().setDefault_sup(false);

            user.addUserSupplier(userSupplier);

            service.save(userSupplier);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", "Fournisseur " +  userSupplier.getSupplier().getLabel() + " ajouté");

            create_new_supplier();
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void updateSupplier() {
        UserSupplierService service = new UserSupplierService(UserSupplier.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            if (userSupplier.getSupplier().getDefault_sup()) {
                message.display(FacesMessage.SEVERITY_INFO, "Erreur", "Impossible de modifier un fournisseur par défaut");

            } else {
                service.save(userSupplier);

                transaction.commit();

                message.display(FacesMessage.SEVERITY_INFO, "Success", "Fournisseur modifié");
            }
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void deleteSupplier(UserSupplier userSupplier) {
        UserSupplierService service = new UserSupplierService(UserSupplier.class);

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {

            service.delete(userSupplier.getId());

            transaction.commit();

            user.removeSupplier(userSupplier);

            message.display(FacesMessage.SEVERITY_INFO, "Success", "Fournisseur supprimer");
        } catch (Exception e) {
            message.display(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible de supprimer ce fournisseur. Celui-ci est reprit dans un ou plusieurs frais");
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();

        }
    }

    public UserSupplier getUserSupplier() {
        return userSupplier;
    }

    public void setUserSupplier(UserSupplier userSupplier) {
        this.userSupplier = userSupplier;
    }
}