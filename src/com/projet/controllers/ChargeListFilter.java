package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Charge;
import com.projet.entities.User;
import com.projet.enumeration.ChargeDateFilterEnum;
import com.projet.enumeration.ChargeStatus;
import com.projet.security.SecurityManager;
import com.projet.services.ChargeService;
import com.projet.utils.DateManager;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 18/09/2020
 * Time: 17:09
 * =================================================================
 */
@Named("chargeListFilter")
@SessionScoped
public class ChargeListFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private ChargeDateFilterEnum dateFilter;
    private ChargeStatus chargeStatus;
    private ChargeDateFilterEnum choosenFilter;
    private Date date_period_start_at;
    private Date date_period_end_at;
    private int pageNumber;
    private int pageSize;

    private User user;

    @PostConstruct
    public void init() {
        resetFilter();
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
    }

    public List<Charge> getUserChargeList() {
        ChargeService service = new ChargeService(Charge.class);

        if (dateFilter != null)
            if (! dateFilter.equals(ChargeDateFilterEnum.PERSONALIZED))
                setPeriodFromFilter();

         return service.getFilteredChargeList(chargeStatus, date_period_start_at, date_period_end_at, 0, 0, user);
    }

    private void setPeriodFromFilter() {
        Date currentDate = new Date();
        Date calculated_date;

        switch (dateFilter) {
            case MONTH:
            case LASTMONTH:
                calculated_date = DateManager.addMonth(currentDate, dateFilter.getNumber());

                date_period_start_at = DateManager.getFirstDateOfMonth(calculated_date);
                date_period_end_at = DateManager.getLastDateOfMonth(calculated_date);
                break;

            case YEAR:
            case LASTYEAR:
                calculated_date = DateManager.addYear(currentDate, dateFilter.getNumber());

                date_period_start_at = DateManager.getFirstDateOfYear(calculated_date);
                date_period_end_at = DateManager.getLastDateOfYear(calculated_date);
                break;
        }
    }

    public void resetFilter() {
        this.dateFilter = null;
        this.chargeStatus = null;
        this.choosenFilter = null;
        this.date_period_start_at = null;
        this.date_period_end_at = null;
    }

    public void date_not_before_start_date(FacesContext context, UIComponent comp, Object value) {
        if (date_period_start_at != null) {
            Date endDate = (Date) value;

            if (endDate != null) {
                if (endDate.before(date_period_start_at)) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Ce champ ne peut être antérieur à la date de debut");
                    throw new ValidatorException(message);
                }
            }
        }
    }

    public void onStatusSelect(SelectEvent event) {
        ChargeDateFilterEnum filter = (ChargeDateFilterEnum) event.getObject();

        setChoosenFilter(filter);
    }

    public ChargeDateFilterEnum getPersonnalizedFilter() {
        return ChargeDateFilterEnum.PERSONALIZED;
    }

    public ChargeDateFilterEnum getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(ChargeDateFilterEnum dateFilter) {
        this.dateFilter = dateFilter;
    }

    public ChargeStatus getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(ChargeStatus chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public Date getDate_period_start_at() {
        return date_period_start_at;
    }

    public void setDate_period_start_at(Date date_period_start_at) {
        this.date_period_start_at = date_period_start_at;
    }

    public Date getDate_period_end_at() {
        return date_period_end_at;
    }

    public void setDate_period_end_at(Date date_period_end_at) {
        this.date_period_end_at = date_period_end_at;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ChargeDateFilterEnum getChoosenFilter() {
        return choosenFilter;
    }

    public void setChoosenFilter(ChargeDateFilterEnum choosenFilter) {
        this.choosenFilter = choosenFilter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}