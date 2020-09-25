package com.projet.services;

import com.projet.entities.Role;
import com.projet.enumeration.RoleEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 16/08/2020
 * Time: 18:34
 * =================================================================
 */
public class RoleService extends Service<Role> implements Serializable {

    RoleService() {
        super();
    }

    public Role findByLabel(RoleEnum label) {
        Map<String, String> param = new HashMap<>();
        param.put("label", label.toString());

        return finder.findOneByNamedQuery("Role.findByLabel", param);
    }

    @Override
    public Role save(Role role) {
        if (role.getId() == 0) {
            em.persist(role);
        } else {
            role = em.merge(role);
        }

        return role;
    }
}
