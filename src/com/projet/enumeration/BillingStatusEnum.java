package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author amaury
 * @project TFE
 * Date: 22/01/2020
 * Time: 19:16
 * =================================================================
 */
public enum BillingStatusEnum {

    VALIDATED("validated"),
    PAYED("payed"),
    DELAYED("delayed");

    private String text;

    private BillingStatusEnum(String text) {
        this.text = text;
    }
}
