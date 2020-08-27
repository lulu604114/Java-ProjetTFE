package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.FinancialAccountService;
import com.projet.services.SupplierService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
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
public class ChargeDetailView implements Serializable {
    private static final long serialVersionUID = 1L;

    private Charge charge;
    private AccountItem accountItem;
    private User user;

    @PostConstruct
    public void init() {
        this.accountItem = new AccountItem();
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
    }

    public String edit(Charge charge) {
        this.charge = charge;

        return "chargeDetail.xhtml?faces-redirect=true";
    }

    public List<FinancialAccount> completeFinancialAccount(String query) {
        FinancialAccountService service = new FinancialAccountService(FinancialAccount.class);

        return service.findByLabelOrCode(this.user, query + "%");
    }

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

    public AccountCategory getFinancialAccountGroup(FinancialAccount account) {
        return account.getCategory();
    }
}