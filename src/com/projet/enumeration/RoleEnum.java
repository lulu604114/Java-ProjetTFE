package com.projet.enumeration;

import com.projet.entities.Role;

import javax.persistence.EntityManager;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 13/08/2020
 * Time: 17:39
 * =================================================================
 */
public enum RoleEnum {

    USER("user"),
    ADMIN("admin");

    private String label;

    RoleEnum(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

}
