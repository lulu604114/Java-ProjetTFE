package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.AccountItemService;
import com.projet.services.ChargeService;
import com.projet.utils.Message;
import org.primefaces.event.SelectEvent;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 21/08/2020
 * Time: 22:59
 * =================================================================
 */
@Named("chargeDetailView")
@SessionScoped
public class ChargeDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);
    private AccountItemService accountItemService = new AccountItemService(AccountItemService.class);

    private Charge charge;
    private AccountItem accountItem;
    private User user;

    private boolean accountItem_amount_has_been_set;
    private boolean accountItem_privatePart_has_been_set;
    private boolean accountItem_taxDeductible_has_been_set;

    @PostConstruct
    public void init() {
        this.accountItem = new AccountItem();
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
    }

    /**
     * When a new accountItem is gonna be created, the accountItem description
     * and amount are set with the description and the amount of the corresponding
     * charge. Amount is set but the taxDeductible and the privatePart are not set.
     * It give the possibility to hidden the deductible amount simulation until
     * all required fields are set.
     */
    public void newItem() {
        accountItem = new AccountItem();
        accountItem.setAmount(charge.getAmount());
        accountItem.setDescription(charge.getLabel());

        accountItem_amount_is_set();
        accountItem_taxDeductible_is_not_set();
        accountItem_privatePart_is_not_set();
    }

    public String getChargeDetail(Charge charge) {
        this.charge = charge;

        return "/app/charge/chargeDetail?faces-redirect=true";
    }

    public boolean does_accountItem_amount_has_been_set() {
        return accountItem_amount_has_been_set;
    }

    public void accountItem_amount_is_not_set() {
        this.accountItem_amount_has_been_set = false;
    }

    public void accountItem_amount_is_set() {
        this.accountItem_amount_has_been_set = true;
    }

    public boolean does_accountItem_privatePart_has_been_set() {
        return accountItem_privatePart_has_been_set;
    }

    public void accountItem_privatePart_is_not_set() {
        this.accountItem_privatePart_has_been_set = false;
    }

    public void accountItem_privatePart_is_set() {
        this.accountItem_privatePart_has_been_set = true;
    }

    public boolean does_accountItem_taxDeductible_has_been_set() {
        return accountItem_taxDeductible_has_been_set;
    }

    public void accountItem_taxDeductible_is_not_set() {
        this.accountItem_taxDeductible_has_been_set = false;
    }

    public void accountItem_taxDeductible_is_set() {
        this.accountItem_taxDeductible_has_been_set = true;
    }

    /**
     * Method used by the autocomplete field.
     * This returns all userAccounts that label
     * or code begin with or contains the query typed by the user.
     * @param query user search.
     * @return the corresponding userAccounts.
     */
    public List<UserAccount> searchUserAccount(String query) {
        List<UserAccount> userAccounts = user.getUserAccounts();
        List<UserAccount> results = new ArrayList<>();

        // check if some userAccounts matches with the query
        for (UserAccount userAccount : userAccounts) {
            if (userAccount.getFinancialAccount().getLabel().toLowerCase().contains(query.toLowerCase()) || userAccount.getFinancialAccount().getCode().startsWith(query)) {
                results.add(userAccount);
            }
        }

        // sort the result to allow them to be grouped by categories
        Collections.sort(results);

        return results;
    }

    /**
     * Display in the autocomplete field the code and the label
     * of a userAccount when it's selected by the user.
     * @param userAccount the selected userAccount
     * @return the concatenation of code and label
     */
    public String display_detailed_userAccount_label(UserAccount userAccount) {
        if (userAccount != null)
            return userAccount.getFinancialAccount().getCode() + " - " + userAccount.getFinancialAccount().getLabel();
        else
            return "";
    }

    /**
     * Convert double in String in html tag where f:convert can't be used
     * @param value
     * @return
     */
    public String convert_double_in_string(double value) {
        String string = String.format("%.2f", value);

        return string.replace(".", ",");
    }

    /**
     * Autocomplete privatePart and tax deductible fields in accountItem form
     * when an userAccount is selected. PrivatePart and taxDeductible fields are set
     * when they contains value.
     * @param event
     */
    public void on_selected_userAccount(SelectEvent event) {
        UserAccount userAccount = (UserAccount) event.getObject();

        if (userAccount != null) {
            accountItem.setPrivatePart(userAccount.getPrivatePart());
            accountItem_privatePart_is_set();

            accountItem.setTaxDeductible(userAccount.getTaxDeductible());
            accountItem_taxDeductible_is_set();
        }
    }

    public String simulate_deductible_amount() {
        if (does_accountItem_amount_has_been_set() && does_accountItem_taxDeductible_has_been_set() && does_accountItem_privatePart_has_been_set()) {
            double total = 0;

            total = accountItemService.calculate_deductible_amount_of_accountItem(accountItem);

            return convert_double_in_string(total);
        } else {
            return "Montant réelement déduit";
        }
    }

    public double getImputedAmount() {
        return accountItemService.calculate_imputed_amount(charge.getAccountItems());
    }

    public double getTotal_deductible_amount() {
        return accountItemService.calculate_deductible_amount_of_accountItem_list(charge.getAccountItems());
    }

    public double calculate_deductible_amount(AccountItem accountItem) {
        return accountItemService.calculate_deductible_amount_of_accountItem(accountItem);
    }

    public void addAccountItem() {
        AccountItemService accountItemService = new AccountItemService(AccountItem.class);
        ChargeService service = new ChargeService(Charge.class);
        EntityTransaction transaction = service.getTransaction();

        transaction.begin();

        try {
            AccountItem accountItem = accountItemService.finalizeAccountItem(this.charge, this.accountItem);

            charge.addAccountItem(accountItem);

            service.save(charge);

            transaction.commit();

            message.display(FacesMessage.SEVERITY_INFO, "Success", accountItem.getDescription() + " is added");
        } finally {
            if (transaction.isActive())
                transaction.rollback();

            service.close();
            accountItemService.close();
        }
    }




    /* GETTER SETTER */
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public AccountItem getAccountItem() {
        return accountItem;
    }

    public void setAccountItem(AccountItem accountItem) {
        this.accountItem = accountItem;
    }
}