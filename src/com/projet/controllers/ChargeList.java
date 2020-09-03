package com.projet.controllers;

import com.projet.conf.App;
import com.projet.enumeration.ChargeFilterEnum;
import com.projet.services.AccountItemService;
import com.projet.services.FinancialYearService;
import com.projet.utils.Message;
import com.projet.entities.*;
import com.projet.enumeration.ChargeStatus;
import com.projet.security.SecurityManager;
import com.projet.services.ChargeService;
import com.projet.utils.DateManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 13/08/2020
 * Time: 20:29
 * =================================================================
 */
@Named("chargeListView")
@ViewScoped
public class ChargeList implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private ChargeService service = new ChargeService(Charge.class);
    private FinancialYearService faService = new FinancialYearService(FinancialYear.class);
    private AccountItemService accountItemService = new AccountItemService(AccountItem.class);


    private User user;
    private List<Charge> chargeList;
    private ChargeFilterEnum filter;

    @PostConstruct
    public void init() {

        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.chargeList = service.getByUser(user);
        Collections.sort(chargeList);
        Collections.reverse(chargeList);
    }

    public List<Charge> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<Charge> chargeList) {
        this.chargeList = chargeList;
    }

    public ChargeFilterEnum getFilter() {
        return filter;
    }

    public void setFilter(ChargeFilterEnum filter) {
        this.filter = filter;
    }

    /**
     * return month name from int. Use to display the month name in rowGrouping
     * @param month
     * @return
     */
    public String getMonth(int month) {
        String monthString = Month.of(month + 1).getDisplayName(TextStyle.FULL, FacesContext.getCurrentInstance().getViewRoot().getLocale());

        return monthString.substring(0, 1).toUpperCase() + monthString.substring(1);
    }

    public int getYear(Date date) {
        return DateManager.getYear(date);
    }

    /**
     * Take the createdAt date of a charge and transform it from the pattern dd/MM/YYYY
     * to MM/YYYY. It give the possibility to a datatable to group charges from a list by month and year.
     * @param charge
     * @return
     */
    public Date getChargeSortDate(Charge charge) {

        return DateManager.getMonthAndYearDate(charge.getCreatedAt());
    }

    public double getTotalLateCharge() {
        return service.totalCharge(chargeList, ChargeStatus.LATE);
    }

    public double getTotalNotPayedCharge() {

        return service.totalCharge(chargeList, ChargeStatus.NOTPAYED) + service.totalCharge(chargeList, ChargeStatus.LATE);
    }

    public double getTotalCharge() {
        return service.totalCharge(chargeList, null);
    }

    public double getTotalDeductibleCharge() {
        FinancialYear financialYear = faService.getCurrentFinancialYearByUser(user);

        return service.calculate_total_deductible_amount_by_financialYear(financialYear);
    }

    public double getAccountItemsIcon(Charge charge) {
        return charge.getAmount() - accountItemService.calculate_imputed_amount(charge.getAccountItems());
    }

    public List<Charge> loadChargeList(ChargeFilterEnum filter) {

       return null;
    }
}
