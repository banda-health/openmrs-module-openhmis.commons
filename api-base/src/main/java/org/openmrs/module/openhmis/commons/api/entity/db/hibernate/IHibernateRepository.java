/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.entity.db.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.openmrs.OpenmrsObject;

/**
 * Represents types that can provide access to a data source through hibernate.
 */
public interface IHibernateRepository {

	/**
	 * @param query The query
	 * @return a new Query Object
	 */
	Query createQuery(String query);

	/**
	 * Creates a new {@link org.hibernate.Criteria}.
	 * @param cls The entity class.
	 * @return The newly created {@link org.hibernate.Criteria}
	 * @should return a new criteria for the entity class
	 */
	<E extends OpenmrsObject> Criteria createCriteria(Class<E> cls);

	/**
	 * Saves an entity to the database, performing either an update or insert depending on the entity state.
	 * @param entity The entity to save.
	 * @return The saved entity.
	 * @should throw an IllegalArgumentException if the entity is null
	 * @should insert a new item into the database
	 * @should update an existing item in the database
	 * @should return a new item with the generated id
	 */
	<E extends OpenmrsObject> E save(E entity);

	/**
	 * Saves a collection of entities to the database.
	 * @param collection The collection to save.
	 */
	void saveAll(Collection<? extends OpenmrsObject> collection);

	/**
	 * Deletes an entity from the database.
	 * @param entity The entity to delete.
	 * @should throw an IllegalArgumentException if the entity is null
	 * @should delete the item from the database
	 * @should not throw an exception if the item is not in the database
	 */
	<E extends OpenmrsObject> void delete(E entity);

	/**
	 * Executes the specified {@link org.hibernate.Criteria} and returns the resulting value.
	 * @param criteria The criteria to execute which must result in a single value.
	 * @param <T> The expected value type.
	 * @return The result of the criteria.
	 */
	<T> T selectValue(Criteria criteria);

	/**
	 * Executes the specified {@link org.hibernate.Query} and returns the resulting value.
	 * @param query The criteria to execute which must result in a single value.
	 * @param <T> The expected value type.
	 * @return The result of the query.
	 */
	<T> T selectValue(Query query);

	/**
	 * Selects a single entity from the database with the specified id.
	 * @param cls The entity class.
	 * @param id The id of the entity.
	 * @return The entity or {@code null} if not found.
	 * @should throw an IllegalArgumentException if the id is null
	 * @should return the entity with the specified id
	 * @should return null if an entity with the id can not be found
	 */
	<E extends OpenmrsObject> E selectSingle(Class<E> cls, Serializable id);

	/**
	 * Selects a single entity from the database using the specified {@link org.hibernate.Criteria}. If more than one entity
	 * is found only the first is returned.
	 * @param cls The entity class.
	 * @param criteria The search {@link org.hibernate.Criteria}.
	 * @return The entity or {@code null} if not found.
	 * @should throw an IllegalArgumentException if the criteria is null
	 * @should return the entity that meets the criteria
	 * @should return null if no entity can be found
	 * @should return the first entity if multiple entities are found
	 */
	<E extends OpenmrsObject> E selectSingle(Class<E> cls, Criteria criteria);

	/**
	 * Selects all entities from the database.
	 * @param cls The entity class.
	 * @return A list of all the entities.
	 * @should return a list of all the entities
	 * @should return an empty list if there are no entities in the database
	 */
	<E extends OpenmrsObject> List<E> select(Class<E> cls);

	/**
	 * Selects the entities from the database that meet the specified {@link org.hibernate.Criteria} .
	 * @param cls The entity class.
	 * @param criteria The search {@link org.hibernate.Criteria}.
	 * @return A list of the entities that were found.
	 * @should throw an IllegalArgumentException if the criteria is null
	 * @should return a list of all entities that meet the criteria
	 * @should return an empty list when no entities are found
	 */
	<E extends OpenmrsObject> List<E> select(Class<E> cls, Criteria criteria);
}
