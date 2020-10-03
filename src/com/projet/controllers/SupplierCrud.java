package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Supplier;
import com.projet.entities.User;
import com.projet.entities.UserSupplier;
import com.projet.security.SecurityManager;
import com.projet.services.UserSupplierService;
import com.projet.utils.Message;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;


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
        create_new_supplier();
    }

    public void create_new_supplier() {
        this.userSupplier = new UserSupplier();
        this.userSupplier.setSupplier(new Supplier());
    }

    public void editSupplier(UserSupplier userSupplier) {
        this.userSupplier = userSupplier;
    }


    public void createSupplier() {
        UserSupplierService service = new UserSupplierService();

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            userSupplier.getSupplier().setDefault_sup(false);

            user.addUserSupplier(userSupplier);

            service.save(userSupplier);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "msg.success", userSupplier.getSupplier().getLabel() + " " + "msg.added");

            create_new_supplier();
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void updateSupplier() {
        UserSupplierService service = new UserSupplierService();

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            if (userSupplier.getSupplier().getDefault_sup()) {
                message.display(FacesMessage.SEVERITY_INFO, "msg.error", "msg.supplier.edit.failed");

            } else {
                service.save(userSupplier);

                transaction.commit();

                message.display(FacesMessage.SEVERITY_INFO, "msg.error", userSupplier.getSupplier().getLabel() + " " + "msg.edit");
            }
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void deleteSupplier(UserSupplier userSupplier) {
        UserSupplierService service = new UserSupplierService();

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {

            service.delete(userSupplier.getId());

            transaction.commit();

            user.removeSupplier(userSupplier);

            message.display(FacesMessage.SEVERITY_INFO, "msg.success", userSupplier.getSupplier().getLabel() + " " + "msg.delete");
        } catch (Exception e) {
            message.display(FacesMessage.SEVERITY_ERROR, "msg.error", "msg.supplier.delete.failed");
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