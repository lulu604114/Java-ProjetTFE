package com.projet.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Renaud DIANA
 * 
 */
public interface EntityFinder<T> {
	
	/**	 
	 * Interface method to find an entity from the database
	 *
	 * @param id
	 *          Backing bean's id to find
	 *
	 * @return 
	 * 			Generic backing bean
	 */	
	public T findOne(int id);
	
	/**	 
	 * Interface method to find a result of entities based on a NamedQuery from the database
	 * 	 
	 * @param namedQuery
	 *          The NamedQuery
	 * @param param
	 *          Query parameters
	 *          For Date params, key must contains the word(ci) 'date'
	 *
	 * @return 
	 * 			List of generic backing beans
	 */	
	public <K, V> List<T> findByNamedQuery(String namedQuery, Map<K, V> param);

	/**
	 * Interface method to find a result of entities based on a NamedQuery from the database
	 *
	 * @param namedQuery
	 *          The NamedQuery
	 * @param param
	 *          Query parameters
	 *          For Date params, key must contains the word(ci) 'date'
	 *
	 * @return
	 * 			List of generic backing beans
	 */
	public <K, V> T findOneByNamedQuery(String namedQuery, Map<K, V> param);
	
	/**	 
	 * Interface method to find a result of entities based on a customQuery from the database
	 * 	 
	 * @param customQuery
	 *          The customQuery
	 * @param param
	 *          Query parameters
	 *          For Date params, key must contains the word(ci) 'date'
	 *          
	 * @return 
	 * 			List of generic backing beans
	 */	
	public <K, V> List<T> findByCustomQuery(String customQuery, Map<K, V> param);



}
