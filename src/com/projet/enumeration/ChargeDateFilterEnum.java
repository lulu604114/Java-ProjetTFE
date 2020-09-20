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
public enum ChargeDateFilterEnum {
    MONTH("Mois en cours", 0),
    LASTMONTH("Mois passé", -1),
    YEAR("Année en cours", 0),
    LASTYEAR("Année passée", -1),
    PERSONALIZED("Periode personnalisée", 0);

    private String label;
    private int number;

    private ChargeDateFilterEnum(String label, int number) {
        this.label = label;
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public int getNumber() {
        return number;
    }
}
