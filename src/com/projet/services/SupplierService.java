package com.projet.services;

import com.projet.entities.Supplier;
import com.projet.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 20/08/2020
 * Time: 17:31
 * =================================================================
 */
public class SupplierService extends Service<Supplier> {

    public SupplierService(Class<?> ec) {
        super(ec);
    }

    public List<Supplier> findByLabel(User user, String label) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("label", label);

        return finder.findByNamedQuery("Supplier.findByLabel", param);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return null;
    }
}
