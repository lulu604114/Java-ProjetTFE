package com.projet.services;

import com.projet.connection.EMF;
import com.projet.dao.EntityFinder;
import com.projet.dao.EntityFinderImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;

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
    protected EntityFinder<E> finder;

    private Class<?> ec;

    Service() {
        this.em = EMF.getEM();
        this.ec = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.finder = new EntityFinderImpl<>(ec, this.em);
    }

    public Service() {

    }

    public E getById (int id) {
        return finder.findOne(id);
    }

    public abstract E save(E e);

    public void delete(int id) {
        E e = (E) em.find(ec, id);
        em.remove(e);
    }

    public EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    public void close() {
        em.close();
    }

    public void refreshCollection(List<E> entityCollection) {
        for (E entity : entityCollection) {
            refreshEntity(entity);
        }
    }

    public void refreshEntity(E entity) {
        if ( ! em.contains(entity))
            entity = em.merge(entity);

        em.refresh(entity);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
