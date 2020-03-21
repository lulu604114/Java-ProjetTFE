package com.projet.enumeration;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author amaury
 * @project TFE
 * Date: 29/01/2020
 * Time: 18:07
 * =================================================================
 */
public enum TypeEnum {

    DOCTOR("doctor"),
    COLLEAGUE("colleague");

    private String text;

    private TypeEnum(String text) {
        this.text = text;
    }
}
