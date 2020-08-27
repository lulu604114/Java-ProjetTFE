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

    NOTPAYED("chargeStatus.notPayed", "tag-secondary"),
    PAYED("chargeStatus.payed", "tag-success"),
    LATE("chargeStatus.late", "tag-danger");

    private String status;
    private String statusClass;

    private ChargeStatus(String status, String statusClass) {
        this.status = status;
        this.statusClass = statusClass;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusClass() {
        return statusClass;
    }
}
