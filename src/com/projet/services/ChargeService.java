package com.projet.services;

import com.projet.entities.Charge;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 13/08/2020
 * Time: 19:08
 * =================================================================
 */
public class ChargeService extends Service<Charge> {


    ChargeService(Class<?> ec) {
        super(ec);
    }

    @Override
    public Charge save(Charge charge) {
        if (charge.getId() == 0) {
            em.persist(charge);
        } else {
            charge = em.merge(charge);
        }

        return charge;
    }




}
