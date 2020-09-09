package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 02/09/2020
 * Time: 23:24
 * =================================================================
 */
public enum SupplierPaimentCondition {
    NOW("Comptant"),
    SEVENDAYS("7 jours"),
    FOURTEENDAYS("14 jours"),
    THIRTYDAYS("30 jours"),
    SIXTYDAYS("60 jours"),
    NINETYDAYS("90 jours");

    private String label;

    SupplierPaimentCondition(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
