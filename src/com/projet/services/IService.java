package com.projet.services;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 25/03/2020
 * Time: 11:47
 * =================================================================
 */
public interface IService<E> {

    public E getById(int id);

    public abstract E save(E e);

    public void delete(E e);
}
