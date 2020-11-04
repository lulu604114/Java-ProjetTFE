package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Charge;
import com.projet.entities.Diary;
import com.projet.entities.User;
import com.projet.entities.UserSupplier;
import com.projet.enumeration.ChargeStatus;
import com.projet.security.SecurityManager;
import com.projet.services.ChargeService;
import com.projet.utils.Message;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 03/09/2020
 * Time: 13:23
 * =================================================================
 */
@Named("chargeCrud")
@ViewScoped
public class ChargeCrud implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    @Inject
    private ChargeDetail chargeDetail;
    @Inject
    private ChargeList chargeList;

    private Charge charge;
    private User user;
    private boolean chargePayed;

    @PostConstruct
    public void init() {
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        create_new_charge();
    }

    public void create_new_charge() {
        this.charge = new Charge();
        this.chargePayed = charge.isPayed();
    }

    public void editCharge(Charge charge) {
        this.charge = charge;
        this.chargePayed = charge.isPayed();
    }

    public List<UserSupplier> completeSupplier(String query) {
        List<UserSupplier> userSuppliers = user.getUserSuppliers();

        List<UserSupplier> suppliers = new ArrayList<>();
        for (UserSupplier userSupplier : userSuppliers) {
            if (userSupplier.getSupplier().getLabel().startsWith(query)) {
                suppliers.add(userSupplier);
            }
        }
        return suppliers;
    }

    public List<ChargeStatus> getChargeStatus() {
        List<ChargeStatus> status = new ArrayList<>();

        status.add(ChargeStatus.NOTPAYED);
        status.add(ChargeStatus.PAYED);

        return status;
    }

    public void onStatusSelect(ValueChangeEvent event) {
        ChargeStatus status = (ChargeStatus) event.getNewValue();

        if (status.equals(ChargeStatus.NOTPAYED))
            setChargePayed(false);
        else if (status.equals(ChargeStatus.PAYED))
            setChargePayed(true);
    }

    public List<Diary> getDiaries() {
        return user.getDiaries();
    }

    public String createCharge() {
        ChargeService service = new ChargeService();

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            if (chargePayed)
                service.mark_charge_as_payed(charge);

            Charge charge = service.createcharge(this.charge, user);

            service.save(charge);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "msg.success", charge.getLabel() + " " + message.translate("msg.added"));

            return chargeDetail.getChargeDetail(charge);
        }finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }

    }

    public void updateCharge() {
        ChargeService service = new ChargeService();

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {

            if (chargePayed)
                service.mark_charge_as_payed(charge);
            else
                service.mark_charge_as_not_payed(charge);

            charge = service.modifyCharge(charge, user);

            service.save(charge);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "msg.success", charge.getLabel() + " " + "msg.edit");

        }finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public String deleteCharge(Charge charge) {
        ChargeService service = new ChargeService();

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            user.removeCharge(charge);

            chargeList.getChargeList().remove(charge);

            service.delete(charge.getId());

            transaction.commit();

            chargeList.applyFilter();

            message.display(FacesMessage.SEVERITY_INFO, "msg.success", charge.getLabel() + " " + message.translate("msg.delete"));

            return "success";
        }finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public void markAsPayed() {
        ChargeService service = new ChargeService();

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            charge = service.mark_charge_as_payed(charge);

            service.save(charge);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "msg.success", charge.getLabel() + " " + message.translate("msg.markAsPayed"));

        }finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
        }
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public boolean isChargePayed() {
        return chargePayed;
    }

    public void setChargePayed(boolean chargePayed) {
        this.chargePayed = chargePayed;
    }

    public void date_not_before_createdAt_date(FacesContext context, UIComponent comp, Object value) {
        if (charge.getCreatedAt() != null) {
            Date paidAt = (Date) value;

            if (paidAt != null) {
                if (paidAt.before(charge.getCreatedAt())) {
                    FacesMessage FMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate("msg.error"), message.translate("msg.validation.notBeforeCreatedAt"));
                    throw new ValidatorException(FMessage);
                }
            }
        }
    }
}