package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE
 * Date: 09/02/2020
 * Time: 18:50
 * =================================================================
 */
public enum ChargeStatus {

    NOTPAYED("chargeStatus.notPayed"),
    PAYED("chargeStatus.payed"),
    LATE("chargeStatus.late");

    private String status;

    private ChargeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
