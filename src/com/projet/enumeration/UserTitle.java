package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 25/03/2020
 * Time: 16:38
 * =================================================================
 */
public enum UserTitle {
    MR("Monsieur", "Mr."),
    MME("Madame", "Mme"),
    MELLE("Mademoiselle", "Melle"),
    DOCTOR("Docteur", "Dr."),
    PROF("Professeur", "Pr."),
    NONE("Aucun", "");

    private String label;
    private String shortLabel;

    UserTitle(String label, String shortLabel) {
        this.label = label;
        this.shortLabel = shortLabel;
    }

    public String getLabel() {
        return label;
    }

    public String getShortLabel() {
        return shortLabel;
    }


}
