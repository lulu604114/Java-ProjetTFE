package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 25/03/2020
 * Time: 19:57
 * =================================================================
 */
public enum Language {

    FR("Francais", "fr"),
    EN("English", "en"),
    DE("Deutsch", "de"),
    NL("Nederland", "nl");

    private String label;
    private String locale;

    Language(String label, String locale) {
        this.label = label;
        this.locale = locale;
    }

    public String getLabel() {
        return label;
    }

    public String getLocale() {
        return locale;
    }
}
