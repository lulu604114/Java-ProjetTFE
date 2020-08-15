package com.projet.enumeration;

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

    USER(1),
    ADMIN(2);

    private int id;

    RoleEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
