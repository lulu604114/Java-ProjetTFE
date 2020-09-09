package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 24/08/2020
 * Time: 12:38
 * =================================================================
 */
public enum PaiementMethodEnum {
    TRANSFER("Virement"),
    CREDIT("Carte de credit"),
    DEBIT("Carte de debit"),
    MONEY("Liquide"),
    PAYCONIQ("Payconiq"),
    DOMICILIATION("Domiciliation"),
    OTHER("Autre");

    private String label;

    PaiementMethodEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
