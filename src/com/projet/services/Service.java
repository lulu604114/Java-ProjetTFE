package com.projet.services;

import com.projet.connection.EMF;
import com.projet.dao.EntityFinder;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.User;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 07/08/2020
 * Time: 20:14
 * =================================================================
 */
public abstract class Service<E> implements IService<E> {

    protected EntityManager em;

    EntityFinder<E> finder;

    Service(Class<?> ec) {
        this.em = EMF.getEM();
        this.finder = new EntityFinderImpl<>(ec, this.em);
    }

    public E getById (int id) {
        return finder.findOne(id);
    }

    public abstract E save(E e);

    public void delete(E e) {
        if (em.contains(e)) {
            em.merge(e);
        }

        em.remove(e);
    }

    public EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    public void close() {
        em.close();
    }
}
