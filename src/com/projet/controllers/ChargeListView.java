package com.projet.controllers;

import com.projet.conf.App;
import com.projet.services.FinancialYearService;
import com.projet.utility.Message;
import com.projet.entities.*;
import com.projet.enumeration.ChargeStatus;
import com.projet.security.SecurityManager;
import com.projet.services.ChargeService;
import com.projet.services.SupplierService;
import com.projet.utility.DateManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

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
public class ChargeListView implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);
    private ChargeService service = new ChargeService(Charge.class);
    private FinancialYearService faService = new FinancialYearService(FinancialYear.class);

    private User user;
    private List<Charge> chargeList;
    private Charge charge;

    @PostConstruct
    public void init() {

        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.chargeList = service.getByUser(user);
        Collections.sort(chargeList);
        Collections.reverse(chargeList);
        this.charge = new Charge();
    }

    public List<Charge> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<Charge> chargeList) {
        this.chargeList = chargeList;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public void initilizeCharge() {
        this.charge = new Charge();
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

    public List<Diary> getDiaries() {
        return user.getDiaries();
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

    public String CreateCharge() {

        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            Charge charge = service.createcharge(this.charge, user);

            service.save(charge);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", charge.getLabel() + " is added");

            return "/app/charge/chargeDetail?faces-redirect=truechargeId=" + charge.getId();
        }finally {
            if (transaction.isActive()){
                transaction.rollback();

                message.display(FacesMessage.SEVERITY_ERROR, "Unknown error", "Please retry");
            }

            service.close();
        }
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
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        return service.totalDeductibleCharge(calendar.get(Calendar.YEAR), user);
    }

    public double getAccountItemsIcon(Charge charge) {
        double total = 0;
        double chargeAmount = charge.getAmount();

        List<AccountItem> accountItems = charge.getAccountItems();
        if (!accountItems.isEmpty()) {
            for (AccountItem item : accountItems) {
                total += item.getAmount();
            }
        }

        return chargeAmount - total;
    }


}
