package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 30/08/2020
 * Time: 13:55
 * =================================================================
 */
public enum ChargeFilterEnum {
    MONTH("Mois en cours"),
    LASTMONTH("Mois passé"),
    YEAR("Année en cours"),
    LASTYEAR("Année passée"),
    PERSONALIZED("Periode personnalisée");

    private String label;

    private ChargeFilterEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
