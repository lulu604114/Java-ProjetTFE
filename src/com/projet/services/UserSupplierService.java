package com.projet.services;

import com.projet.entities.Supplier;
import com.projet.entities.User;
import com.projet.entities.UserSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 29/08/2020
 * Time: 16:50
 * =================================================================
 */
public class UserSupplierService extends Service<UserSupplier>{

    public UserSupplierService() {
        super();
    }

    public List<UserSupplier> getSupplierByUser(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);

        return finder.findByNamedQuery("US.findByUser", param);
    }

    @Override
    public UserSupplier save(UserSupplier userSupplier) {
        if (userSupplier.getId() == 0) {
            em.persist(userSupplier);
        } else {
            userSupplier = em.merge(userSupplier);
        }

        return userSupplier;
    }

    public List<UserSupplier> createDefaultSupplier(User user) {
        SupplierService service = new SupplierService();
        List<Supplier> suppliers = service.findDefaultSupplier();
        List<UserSupplier> userSuppliers = new ArrayList<>();

        for (Supplier supplier: suppliers) {
            UserSupplier userSupplier = new UserSupplier();
            userSupplier.setSupplier(supplier);
            userSupplier.setUser(user);

            userSuppliers.add(userSupplier);
        }

        return userSuppliers;

    }
}
