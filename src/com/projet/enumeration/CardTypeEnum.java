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
public enum CardTypeEnum {

    APPOINTMENT("rdv"),
    TASK("Tache");

    private String text;

    private CardTypeEnum(String text) {
        this.text = text;
    }
}
