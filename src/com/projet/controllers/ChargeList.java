package com.projet.controllers;

import com.projet.conf.App;
import com.projet.services.AccountItemService;
import com.projet.services.FinancialYearService;
import com.projet.services.UserService;
import com.projet.utils.Message;
import com.projet.entities.*;
import com.projet.enumeration.ChargeStatus;
import com.projet.security.SecurityManager;
import com.projet.services.ChargeService;
import com.projet.utils.DateManager;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

    @Inject
    private ChargeListFilter filter;

    private User user;

    /**
     * Result of the getFilteredChargeList query without pagination use to make global calculation
     * @see #getTotalLateCharge()
     * @see #getTotalNotPayedCharge()
     * @see #getTotalCharge()
     */
    private List<Charge> chargeList;

    private LazyDataModel<Charge> model;

    @PostConstruct
    public void init() {
        UserService userService = new UserService();
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        userService.refreshEntity(this.user);
        applyFilter();

        /**
         * Lazy loading of chargeList using lazyDataModel from primefaces. For more information :
         * @see <a href="https://www.primefaces.org/showcase/ui/data/datatable/lazy.xhtml">Primefaces showcase</a>
         */

        this.model = new LazyDataModel<Charge>() {
            private List<Charge> lazyChargeList;

            @Override
            public List<Charge> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                ChargeService service = new ChargeService();

                // Count total row return by the query
                long rowCount = service.countChargeList(filter.getChargeStatus(), filter.getDate_period_start_at(), filter.getDate_period_end_at(), user);

                // Set row count for pagination
                setRowCount(Long.valueOf(rowCount).intValue());

                // Fix correct pagination when last row on page has been deleted
                if (rowCount  > 0 && first >= rowCount) {
                    int numberOfPages = (int) Math.ceil(rowCount * 1d / pageSize);
                    first = Math.max((numberOfPages - 1) * pageSize, 0);
                }

                // get data with filter and pagination
                lazyChargeList = service.getFilteredChargeList(filter.getChargeStatus(), filter.getDate_period_start_at(), filter.getDate_period_end_at(), first, pageSize, user);


                return lazyChargeList;
            }

            @Override
            public Object getRowKey(Charge charge) {
                return charge.getId();
            }

            @Override
            public Charge getRowData(String rowKey) {
                for (Charge charge : lazyChargeList) {

                    if (charge.getId() == Integer.parseInt(rowKey)) {
                        return charge;
                    }
                }

                return null;
            }
        };
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
        ChargeService service = new ChargeService();

        return service.totalCharge(chargeList, ChargeStatus.LATE);
    }

    public double getTotalNotPayedCharge() {
        ChargeService service = new ChargeService();

        return service.totalCharge(chargeList, ChargeStatus.NOTPAYED) + service.totalCharge(chargeList, ChargeStatus.LATE);
    }

    public double getTotalCharge() {
        ChargeService service = new ChargeService();

        return service.totalCharge(chargeList, null);
    }

    public double getTotalDeductibleCharge() {
        FinancialYearService faService = new FinancialYearService();
        ChargeService service = new ChargeService();

        FinancialYear financialYear = faService.getCurrentFinancialYearByUser(user);

        return service.calculate_total_deductible_amount_by_financialYear(financialYear);
    }

    /**
     * Return the amount that remains to be imputed
     * @param charge
     * @return
     */
    public double getAccountItemsIcon(Charge charge) {
        AccountItemService accountItemService = new AccountItemService();

        return charge.getAmount() - accountItemService.calculate_imputed_amount(charge.getAccountItems());
    }

    /**
     * Apply filter and reload data with the filter
     */
    public void applyFilter() {
        filter.applyFilter();

        ChargeService service = new ChargeService();

        this.chargeList = service.getFilteredChargeList(filter.getChargeStatus(), filter.getDate_period_start_at(), filter.getDate_period_end_at(), 0, 0, user);
    }

    /**
     * Disable all filter selection
     */
    public void resetFilter() {
        filter.resetFilter();
        applyFilter();
    }




    public List<Charge> getChargeList() {
        return this.chargeList;
    }

    public void setChargeList(List<Charge> chargeList) {
        this.chargeList = chargeList;
    }

    public LazyDataModel<Charge> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<Charge> model) {
        this.model = model;
    }
}
