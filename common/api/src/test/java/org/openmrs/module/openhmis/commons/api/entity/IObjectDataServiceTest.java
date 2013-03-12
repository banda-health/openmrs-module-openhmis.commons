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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class IObjectDataServiceTest<S extends IObjectDataService<E>, E extends BaseOpenmrsObject>
		extends BaseModuleContextSensitiveTest {
	protected S service;

	protected abstract E createEntity(boolean valid);
	protected abstract int getTestEntityCount();
	protected abstract void updateEntityFields(E entity);

	protected void assertEntity(E expected, E actual) {
		Assert.assertNotNull(expected);
		Assert.assertNotNull(actual);

		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUuid(), actual.getUuid());
	}

	protected S createService() {
		return Context.getService(getServiceClass());
	}

	@Before
	public void before() throws Exception{
		service = createService();
	}

	/**
	 * @verifies throw NullPointerException if the object is null
	 * @see IObjectDataService#save(org.openmrs.OpenmrsObject)
	 */
	@Test(expected = NullPointerException.class)
	public void save_shouldThrowNullPointerExceptionIfTheObjectIsNull() throws Exception {
		service.save(null);
	}

	/**
	 * @verifies validate the object before saving
	 * @see IObjectDataService#save(org.openmrs.OpenmrsObject)
	 */
	@Test(expected = APIException.class)
	public void save_shouldValidateTheObjectBeforeSaving() throws Exception {
		E entity = createEntity(false);

		service.save(entity);
	}

	/**
	 * @verifies return saved object
	 * @see IObjectDataService#save(org.openmrs.OpenmrsObject)
	 */
	@Test
	public void save_shouldReturnSavedObject() throws Exception {
		E entity = createEntity(true);

		E result = service.save(entity);
		Context.flushSession();

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
	}

	/**
	 * @verifies update the object successfully
	 * @see IObjectDataService#save(org.openmrs.OpenmrsObject)
	 */
	@Test
	public void save_shouldUpdateTheObjectSuccessfully() throws Exception {
		E entity = service.getById(0);
		Assert.assertNotNull(entity);

		updateEntityFields(entity);

		service.save(entity);
		Context.flushSession();

		E updatedEntity = service.getById(entity.getId());
		assertEntity(entity, updatedEntity);
	}

	/**
	 * @verifies create the object successfully
	 * @see IObjectDataService#save(org.openmrs.OpenmrsObject)
	 */
	@Test
	public void save_shouldCreateTheObjectSuccessfully() throws Exception {
		E entity = createEntity(true);

		entity = service.save(entity);
		Context.flushSession();

		E result = service.getById(entity.getId());
		assertEntity(entity, result);
	}

	/**
	 * @verifies throw NullPointerException if the object is null
	 * @see IObjectDataService#purge(org.openmrs.OpenmrsObject)
	 */
	@Test(expected = NullPointerException.class)
	public void purge_shouldThrowNullPointerExceptionIfTheObjectIsNull() throws Exception {
		service.purge(null);
	}

	/**
	 * @verifies delete the specified object
	 * @see IObjectDataService#purge(org.openmrs.OpenmrsObject)
	 */
	@Test
	public void purge_shouldDeleteTheSpecifiedObject() throws Exception {
		E entity = service.getById(0);
		Assert.assertNotNull(entity);

		service.purge(entity);
		Context.flushSession();

		entity = service.getById(0);
		Assert.assertNull(entity);
	}

	/**
	 * @verifies return all object records
	 * @see IObjectDataService#getAll()
	 */
	@Test
	public void getAll_shouldReturnAllObjectRecords() throws Exception {
		List<E> entities = service.getAll();
		Assert.assertNotNull(entities);

		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies return an empty list if there are no objects
	 * @see IObjectDataService#getAll()
	 */
	@Test
	public void getAll_shouldReturnAnEmptyListIfThereAreNoObjects() throws Exception {
		List<E> entities = service.getAll();
		for (E entity : entities) {
			service.purge(entity);
		}

		Context.flushSession();

		entities = service.getAll();
		Assert.assertNotNull(entities);
		Assert.assertEquals(0, entities.size());
	}

	/**
	 * @verifies return the object with the specified id
	 * @see IObjectDataService#getById(int)
	 */
	@Test
	public void getById_shouldReturnTheObjectWithTheSpecifiedId() throws Exception {
		E entity = service.getById(0);

		Assert.assertEquals((Integer) 0, entity.getId());
	}

	/**
	 * @verifies return null if no object can be found.
	 * @see IObjectDataService#getById(int)
	 */
	@Test
	public void getById_shouldReturnNullIfNoObjectCanBeFound() throws Exception {
		E entity = service.getById(-100);

		Assert.assertNull(entity);
	}

	/**
	 * @verifies find the object with the specified uuid
	 * @see IObjectDataService#getByUuid(String)
	 */
	@Test
	public void getByUuid_shouldFindTheObjectWithTheSpecifiedUuid() throws Exception {
		E entity = service.getById(0);
		E uuidEntity = service.getByUuid(entity.getUuid());

		assertEntity(entity, uuidEntity);
	}

	/**
	 * @verifies return null if no object is found
	 * @see IObjectDataService#getByUuid(String)
	 */
	@Test
	public void getByUuid_shouldReturnNullIfNoObjectIsFound() throws Exception {
		E entity = service.getByUuid("Invalid");

		Assert.assertNull(entity);
	}

	/**
	 * @verifies throw IllegalArgumentException if uuid is null
	 * @see IObjectDataService#getByUuid(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getByUuid_shouldThrowIllegalArgumentExceptionIfUuidIsNull() throws Exception {
		service.getByUuid(null);
	}

	/**
	 * @verifies throw IllegalArgumentException if uuid is empty
	 * @see IObjectDataService#getByUuid(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getByUuid_shouldThrowIllegalArgumentExceptionIfUuidIsEmpty() throws Exception {
		service.getByUuid("");
	}

	/**
	 * @verifies return all object records if paging is null
	 * @see IObjectDataService#getAll(org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnAllObjectRecordsIfPagingIsNull() throws Exception {
		List<E> entities = service.getAll(null);

		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies return all object records if paging page or size is less than one
	 * @see IObjectDataService#getAll(org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnAllObjectRecordsIfPagingPageOrSizeIsLessThanOne() throws Exception {
		PagingInfo paging = new PagingInfo(0, 1);
		List<E> entities = service.getAll(paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());

		paging = new PagingInfo(1, 0);
		entities = service.getAll(paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies set the paging total records to the total number of object records
	 * @see IObjectDataService#getAll(org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldSetThePagingTotalRecordsToTheTotalNumberOfObjectRecords() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities = service.getAll(paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals(Long.valueOf(getTestEntityCount()), paging.getTotalRecordCount());
	}

	/**
	 * @verifies not get the total paging record count if it is more than zero
	 * @see IObjectDataService#getAll(org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldNotGetTheTotalPagingRecordCountIfItIsMoreThanZero() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);
		paging.setLoadRecordCount(false);
		List<E> entities = service.getAll(paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertNull(paging.getTotalRecordCount());
	}

	/**
	 * @verifies return paged object records if paging is specified
	 * @see IObjectDataService#getAll(org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnPagedObjectRecordsIfPagingIsSpecified() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);

		List<E> entities;
		for (int i = 0; i < getTestEntityCount(); i++) {
			paging.setPage(i + 1);
			entities = service.getAll(paging);

			Assert.assertNotNull(entities);
			Assert.assertEquals(1, entities.size());
			Assert.assertEquals(i, (int)entities.get(0).getId());
		}
	}

	@SuppressWarnings("unchecked")
	protected Class<S> getServiceClass() {
		ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();

		return (Class) parameterizedType.getActualTypeArguments()[0];
	}
}
