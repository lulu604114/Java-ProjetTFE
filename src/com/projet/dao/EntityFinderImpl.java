package com.projet.dao;

import com.projet.connection.EMF;
import com.projet.entities.Patient;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.*;

/**
 * Class to perform entity CRUD with the database
 *
 * @author Renaud DIANA
 */
public class EntityFinderImpl<T> implements EntityFinder<T>, Serializable {
	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private Class<?> ec;

	// Log4j
	private static Logger log = Logger.getLogger(EntityFinderImpl.class);

	/**
     * Default constructor
     */
	public EntityFinderImpl(Class<?> ec){
		super();
		this.ec = ec;
	}

	public EntityFinderImpl(Class<?> ec, EntityManager em){
		super();
		this.ec = ec;
		this.em = em;
	}

	@Override
	public T findOne(int id) {

		 T t = (T)em.find(ec, id);

		log.debug("Bean " + ec.getSimpleName() + " find from database: Ok");

		return t;
	}

	@Override
	public <K, V> List<T> findByNamedQuery(String namedQuery, Map<K, V> param) {

		List<T> listT;

		Query query = em.createNamedQuery(namedQuery, ec);

		if(param != null) {

			setParameters(query, param);
		}

		listT = (List<T>) query.getResultList();

		log.debug("List " + ec.getSimpleName() + " size: " + listT.size());
		log.debug("Named query " + namedQuery + " find from database: Ok");

		return listT;
	}

	@Override
	public <K, V> T findOneByNamedQuery(String namedQuery, Map<K, V> param) {

		List<T> listT;
		T t;

		listT = findByNamedQuery(namedQuery, param);

		if (listT.isEmpty()) {
			t = null;
		} else {
			t = listT.get(0);
		}

		return t;
	}

	@Override
	public <K, V> List<T> findByCustomQuery(String customQuery, Map<K, V> param) {

		List<T> listT;

		Query query = em.createQuery(customQuery, ec);

		if(param != null) {

			setParameters(query, param);
		}

		listT = (List<T>) query.getResultList();

		log.debug("List " + ec.getSimpleName() + " size: " + listT.size());
		log.debug("Custom query " + customQuery + " find from database: Ok");

		return listT;
	}

	/**
	 * @param query
	 * @param param
	 * @return
	 * 			the query with parameters
	 */
	private <K, V> void setParameters(Query query, Map<K, V> param) {

		Set<Map.Entry<K, V>> entries = param.entrySet();
		for (Map.Entry<K, V> entry : entries) {
			if ((boolean) entry.getKey().toString().toLowerCase().contains("date"))
				query.setParameter((String) entry.getKey(), (Date) entry.getValue(), TemporalType.DATE);
			else
				query.setParameter((String) entry.getKey(), entry.getValue());
			//log.debug("entry.getValue: " + entry.getValue());
		}
	}

}
