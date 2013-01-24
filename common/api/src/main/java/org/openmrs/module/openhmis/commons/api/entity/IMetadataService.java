/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
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

import org.openmrs.OpenmrsMetadata;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Represents classes that provide data access services to model types that inherit from {@link org.openmrs.OpenmrsMetadata}.
 * @param <E> The entity model class.
 */
@Transactional
public interface IMetadataService<E extends OpenmrsMetadata> extends IEntityService<E> {
	/**
	 * Retires the specified entity. This effectively removes the entity from circulation or use.
	 *
	 * @param entity entity to be retired.
	 * @param reason the reason why the entity is being retired.
	 * @return the newly retired entity.
	 * @should retire the entity successfully
	 * @should throw NullPointerException when the entity is null
	 * @should throw IllegalArgumentException when no reason is given
	 */
	E retire(E entity, String reason) throws APIException;

	/**
	 * Unretire the specified entity. This restores a previously retired entity back into circulation and use.
	 *
	 * @param entity The entity to unretire.
	 * @return the newly unretired entity.
	 * @throws org.openmrs.api.APIException
	 * @should throw NullPointerException if the entity is null
	 * @should unretire the entity
	 */
	E unretire(E entity) throws APIException;

	/**
	 * Returns all entity records that have the specified retirement status.
	 * @param includeRetired {@code true} to include retired entities.
	 * @return All the entity records that have the specified retirement status.
	 * @throws org.openmrs.api.APIException
	 * @should return all entities when includeRetired is set to true
	 * @should return all unretired entities when retired is set to false
	 */
	List<E> getAll(boolean includeRetired) throws APIException;

	/**
	 * Returns all entity records that have the specified retirement status and paging.
	 * @param includeRetired {@code true} to include retired entities.
	 * @param paging The paging information.
	 * @return All the entity records that have the specified retirement status.
	 * @throws org.openmrs.api.APIException
	 * @should return all entities when include retired is set to true
	 * @should return all unretired entities when retired is set to false
	 * @should return all specified entity records if paging is null
	 * @should return all specified entity records if paging page or size is less than one
	 * @should set the paging total records to the total number of entity records
	 * @should not get the total paging record count if it is more than zero
	 * @should return paged entity records if paging is specified
	 */
	List<E> getAll(boolean includeRetired, PagingInfo paging) throws APIException;

	/**
	 * Finds all the entities that start with the specified name.
	 * @param nameFragment The name fragment.
	 * @param includeRetired Whether retired items should be included in the results.
	 * @return All items that start with the specified name.
	 * @throws org.openmrs.api.APIException
	 * @should throw IllegalArgumentException if the name is null
	 * @should throw IllegalArgumentException if the name is empty
	 * @should throw IllegalArgumentException if the name is longer than 255 characters
	 * @should return an empty list if no entities are found
	 * @should not return retired entities unless specified
	 * @should return entities that start with the specified name
	 */
	List<E> findByName(String nameFragment, boolean includeRetired) throws APIException;

	/**
	 * Finds all the entities that start with the specified name and paging.
	 * @param nameFragment The name fragment.
	 * @param includeRetired Whether retired items should be included in the results.
	 * @param paging The paging information.
	 * @return All items that start with the specified name.
	 * @throws org.openmrs.api.APIException
	 * @should throw IllegalArgumentException if the name is null
	 * @should throw IllegalArgumentException if the name is empty
	 * @should throw IllegalArgumentException if the name is longer than 255 characters
	 * @should return an empty list if no entities are found
	 * @should not return retired entities unless specified
	 * @should return entities that start with the specified name
	 * @should return all specified entity records if paging is null
	 * @should return all specified entity records if paging page or size is less than one
	 * @should set the paging total records to the total number of entity records
	 * @should not get the total paging record count if it is more than zero
	 * @should return paged entity records if paging is specified
	 */
	List<E> findByName(String nameFragment, boolean includeRetired, PagingInfo paging) throws APIException;
}
