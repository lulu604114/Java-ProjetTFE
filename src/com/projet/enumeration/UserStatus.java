package com.projet.enumeration;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 25/03/2020
 * Time: 20:12
 * =================================================================
 */
public enum UserStatus {
    PRO("Independant"),
    EMPLOYED("Employe"),
    UNEMPLOYED("A la rechercher d'emploi"),
    PROCOMP("Independant complementaire"),
    STUDENT("Etudiant"),
    NONE("Aucun");

    private String label;

    UserStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
