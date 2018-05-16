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
package org.openmrs.module.openhmis.commons.api.entity;

import java.util.Collection;
import java.util.List;

import org.openmrs.OpenmrsObject;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.db.hibernate.IHibernateRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represents classes that provide data access services to model types that implement {@link org.openmrs.OpenmrsObject}.
 * @param <E> The {@link org.openmrs.OpenmrsObject} model class.
 */
@Transactional
public interface IObjectDataService<E extends OpenmrsObject> extends OpenmrsService {
	/**
	 * Set the data repository object that the service will use to interact with the database. This is set by spring in the
	 * applicationContext-service.xml file
	 * @param repository The data repository object that the service will use
	 */
	void setRepository(IHibernateRepository repository);

	/**
	 * Saves the object to the database, creating a new object or updating an existing one.
	 * @param object The object to be saved to the database
	 * @return The saved object.
	 * @should throw NullPointerException if the object is null
	 * @should validate the object before saving
	 * @should return saved object
	 * @should update the object successfully
	 * @should create the object successfully
	 */
	E save(E object);

	/**
	 * Saves an object to the database along with the specified related {@link OpenmrsObject}'s within a single transaction.
	 * @param object The object to be saved to the database
	 * @param related The related objects to be saved to the database
	 * @return The saved object.
	 */
	E saveAll(E object, Collection<? extends OpenmrsObject> related);

	/**
	 * Saves a collection of objects to the database
	 * @param related The related objects to be saved to the database
	 */
	void saveAll(Collection<? extends OpenmrsObject> collection);

	/**
	 * Completely remove an object from the database (not reversible).
	 * @param object the object to remove from the database.
	 * @should throw NullPointerException if the object is null
	 * @should delete the specified object
	 */
	void purge(E object);

	/**
	 * Returns all object records.
	 * @return All object records that are in the database.
	 * @should return all object records
	 * @should return an empty list if there are no objects
	 */
	@Transactional(readOnly = true)
	List<E> getAll();

	/**
	 * Returns all object records with the specified paging.
	 * @param paging The paging information.
	 * @return All object records that are in the database.
	 * @should return all object records
	 * @should return an empty list if there are no objects
	 * @should return all object records if paging is null
	 * @should return all object records if paging page or size is less than one
	 * @should set the paging total records to the total number of object records
	 * @should not get the total paging record count if it is more than zero
	 * @should return paged object records if paging is specified
	 */
	@Transactional(readOnly = true)
	List<E> getAll(PagingInfo paging);

	/**
	 * Gets the object with the specified id or {@code null} if not found.
	 * @param id The primary key of the object to find.
	 * @return The object with the specified id or {@code null}.
	 * @throws org.openmrs.api.APIException
	 * @should return the object with the specified id
	 * @should return null if no object can be found.
	 */
	@Transactional(readOnly = true)
	E getById(int id);

	/**
	 * Gets an object by uuid.
	 * @param uuid is the uuid of the desired object.
	 * @return the object with the specified uuid.
	 * @should find the object with the specified uuid
	 * @should return null if no object is found
	 * @should throw IllegalArgumentException if uuid is null
	 * @should throw IllegalArgumentException if uuid is empty
	 */
	@Transactional(readOnly = true)
	E getByUuid(String uuid);
}
