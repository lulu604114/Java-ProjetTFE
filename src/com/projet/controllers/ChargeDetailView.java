package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.*;
import com.projet.security.SecurityManager;
import com.projet.services.AccountItemService;
import com.projet.services.ChargeService;
import com.projet.utility.Message;
import org.apache.shiro.authc.Account;
import org.primefaces.event.SelectEvent;
import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);
    private ChargeService service = new ChargeService(Charge.class);

    private int chargeId;
    private Charge charge;
    private AccountItem accountItem;
    private User user;

    private boolean setAmount;
    private boolean setPrivate;
    private boolean setDeductible;

    @PostConstruct
    public void init() {
        this.charge = service.getById(chargeId);
        this.accountItem = new AccountItem();
        this.user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
    }

    public void newItem() {
        this.accountItem = new AccountItem();
        this.accountItem.setAmount(this.charge.getAmount());
        this.accountItem.setDescription(this.charge.getLabel());

        this.setAmount = true;
        this.setDeductible = false;
        this.setPrivate = false;
    }

    public List<UserAccount> completeUserAccount(String query) {
        List<UserAccount> userAccounts = user.getUserAccounts();

        List<UserAccount> results = new ArrayList<>();
        for (UserAccount userAccount : userAccounts) {
            if (userAccount.getFinancialAccount().getLabel().contains(query) || userAccount.getFinancialAccount().getCode().startsWith(query)) {
                results.add(userAccount);
            }
        }

        Collections.sort(results);

        return results;
    }

    public String getUserAccountLabel(UserAccount userAccount) {
        if (userAccount != null)
            return userAccount.getFinancialAccount().getCode() + " - " + userAccount.getFinancialAccount().getLabel();
        else
            return "";
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

    public boolean isSetAmount() {
        return setAmount;
    }

    public void setSetAmount(boolean setAmount) {
        this.setAmount = setAmount;
    }

    public boolean isSetPrivate() {
        return setPrivate;
    }

    public void setSetPrivate(boolean setPrivate) {
        this.setPrivate = setPrivate;
    }

    public boolean isSetDeductible() {
        return setDeductible;
    }

    public void setSetDeductible(boolean setDeductible) {
        this.setDeductible = setDeductible;
    }

    public int getChargeId() {
        return chargeId;
    }

    public void setChargeId(int chargeId) {
        this.chargeId = chargeId;
    }

    public String getStringDouble(double value) {
        String string = String.format("%.2f", value);

        return string.replace(".", ",");
    }

    public void onValueChangeAutocomplete(SelectEvent event) {
        UserAccount userAccount = (UserAccount) event.getObject();

        if (userAccount != null) {
            this.accountItem.setPrivatePart(userAccount.getPrivatePart());
            this.accountItem.setTaxDeductible(userAccount.getTaxDeductible());

            this.setDeductible = true;
            this.setPrivate = true;
        }
    }

    public String getDeductibleAmountField() {
        if (isSetDeductible() && isSetAmount() && isSetPrivate()) {
            double total = 0;

            total = getDeductibleAmount(this.accountItem);

            return getStringDouble(total);
        } else {
            return "Montant réelement déduit";
        }
    }

    public double getImputedAmount() {
        List<AccountItem> accountItems = this.charge.getAccountItems();
        double total = 0;

        for (AccountItem item : accountItems) {
            total += item.getAmount();
        }

        return total;
    }

    public double getTotalDeductibleAmount() {
        List<AccountItem> accountItems = this.charge.getAccountItems();
        double total = 0;

        for (AccountItem item : accountItems) {
            double amount = item.getAmount();
            double privatePart = item.getPrivatePart();
            double deductible = item.getTaxDeductible();

            total += ((amount - ((amount / 100) * privatePart)) / 100) * deductible;
        }

        return total;
    }

    public double getDeductibleAmount(AccountItem accountItem) {

        double amount = accountItem.getAmount();
        double privatePart = accountItem.getPrivatePart();
        double deductible = accountItem.getTaxDeductible();

        return ((amount - ((amount / 100) * privatePart)) / 100) * deductible;
    }

    public void addAccountItem() {
        AccountItemService accountItemService = new AccountItemService(AccountItem.class);
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
}