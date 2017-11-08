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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;

public abstract class IMetadataDataServiceTest<S extends IMetadataDataService<E>, E extends OpenmrsMetadata>
        extends IObjectDataServiceTest<S, E> {

	public static void assertOpenmrsMetadata(OpenmrsMetadata expected, OpenmrsMetadata actual) {
		IObjectDataServiceTest.assertOpenmrsObject(expected, actual);

		Assert.assertEquals(expected.getChangedBy(), actual.getChangedBy());
		Assert.assertEquals(expected.getCreator(), actual.getCreator());
		Assert.assertEquals(expected.getDateChanged(), actual.getDateChanged());
		Assert.assertEquals(expected.getDateCreated(), actual.getDateCreated());
		Assert.assertEquals(expected.getDateRetired(), actual.getDateRetired());
		Assert.assertEquals(expected.getDescription(), actual.getDescription());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.isRetired(), actual.isRetired());
		Assert.assertEquals(expected.getRetiredBy(), actual.getRetiredBy());
		Assert.assertEquals(expected.getRetireReason(), actual.getRetireReason());
	}

	@Override
	protected void assertEntity(E expected, E actual) {
		assertOpenmrsMetadata(expected, actual);
	}

	/**
	 * @verifies retire the metadata successfully
	 * @see IMetadataDataService#retire(org.openmrs.OpenmrsMetadata, String)
	 */
	@Test
	public void retire_shouldRetireTheMetadataSuccessfully() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);

		service.retire(entity, reason);

		Context.flushSession();

		entity = service.getById(entity.getId());

		Assert.assertTrue(entity.isRetired());
		Assert.assertEquals(Context.getAuthenticatedUser(), entity.getRetiredBy());
		Assert.assertEquals(reason, entity.getRetireReason());
		Date now = new Date();
		Assert.assertTrue(entity.getDateRetired().before(now) || entity.getDateRetired().equals(now));
	}

	/**
	 * @verifies throw NullPointerException when the metadata is null
	 * @see IMetadataDataService#retire(org.openmrs.OpenmrsMetadata, String)
	 */
	@Test(expected = NullPointerException.class)
	public void retire_shouldThrowNullPointerExceptionWhenTheMetadataIsNull() throws Exception {
		service.retire(null, "something");
	}

	/**
	 * @verifies throw IllegalArgumentException when no reason is given
	 * @see IMetadataDataService#retire(org.openmrs.OpenmrsMetadata, String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void retire_shouldThrowIllegalArgumentExceptionWhenNoReasonIsGiven() throws Exception {
		E entity = service.getById(0);

		service.retire(entity, null);
	}

	/**
	 * @verifies throw NullPointerException if the metadata is null
	 * @see IMetadataDataService#unretire(org.openmrs.OpenmrsMetadata)
	 */
	@Test(expected = NullPointerException.class)
	public void unretire_shouldThrowNullPointerExceptionIfTheMetadataIsNull() throws Exception {
		service.unretire(null);
	}

	/**
	 * @verifies unretire the metadata
	 * @see IMetadataDataService#unretire(org.openmrs.OpenmrsMetadata)
	 */
	@Test
	public void unretire_shouldUnretireTheMetadata() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		entity = service.getById(entity.getId());
		Date dateRetired = entity.getDateRetired();
		Assert.assertTrue(entity.isRetired());
		service.unretire(entity);

		Context.flushSession();

		entity = service.getById(entity.getId());
		Assert.assertFalse(entity.isRetired());
		Assert.assertNull(entity.getRetiredBy());
		Assert.assertNull(entity.getRetireReason());
		Assert.assertEquals(dateRetired, entity.getDateRetired());
	}

	/**
	 * @verifies return all retired metadata when retired is set to true
	 * @see IMetadataDataService#getAll(boolean)
	 */
	@Test
	public void getAll_shouldReturnAllMetadataWhenIncludeRetiredIsSetToTrue() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		List<E> entities = service.getAll(true);
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies return all unretired metadata when retired is set to false
	 * @see IMetadataDataService#getAll(boolean)
	 */
	@Test
	public void getAll_shouldReturnAllUnretiredMetadataWhenRetiredIsSetToFalse() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		List<E> entities = service.getAll(false);
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount() - 1, entities.size());
	}

	/**
	 * @verifies return all unretired metadata when retired is not specified
	 * @see IMetadataDataService#getAll(boolean)
	 */
	@Test
	public void getAll_shouldReturnAllUnretiredMetadataWhenRetiredIsNotSpecified() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		List<E> entities = service.getAll();
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount() - 1, entities.size());
	}

	@Test
	@Override
	public void getAll_shouldReturnAnEmptyListIfThereAreNoObjects() throws Exception {
		List<E> entities = service.getAll();
		for (E entity : entities) {
			service.retire(entity, "test");
		}

		Context.flushSession();

		entities = service.getAll(false);
		Assert.assertNotNull(entities);
		Assert.assertEquals(0, entities.size());
	}

	/**
	 * @verifies throw IllegalArgumentException if the name is null
	 * @see IMetadataDataService#getByNameFragment(String, boolean)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getByNameFragment_shouldThrowIllegalArgumentExceptionIfTheNameIsNull() throws Exception {
		service.getByNameFragment(null, true);
	}

	/**
	 * @verifies throw IllegalArgumentException if the name is empty
	 * @see IMetadataDataService#getByNameFragment(String, boolean)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getByNameFragment_shouldThrowIllegalArgumentExceptionIfTheNameIsEmpty() throws Exception {
		service.getByNameFragment("", true);
	}

	/**
	 * @verifies throw IllegalArgumentException if the name is longer than 255 characters
	 * @see IMetadataDataService#getByNameFragment(String, boolean)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getByNameFragment_shouldThrowIllegalArgumentExceptionIfTheNameIsLongerThan255Characters() throws Exception {
		service.getByNameFragment(StringUtils.repeat("A", 256), true);
	}

	/**
	 * @verifies return an empty list if no metadata are found
	 * @see IMetadataDataService#getByNameFragment(String, boolean)
	 */
	@Test
	public void getByNameFragment_shouldReturnAnEmptyListIfNoMetadataAreFound() throws Exception {
		List<E> entities = service.getByNameFragment("NotAValidName", true);

		Assert.assertNotNull(entities);
		Assert.assertEquals(0, entities.size());
	}

	/**
	 * @verifies not return retired metadata unless specified
	 * @see IMetadataDataService#getByNameFragment(String, boolean)
	 */
	@Test
	public void getByNameFragment_shouldNotReturnRetiredMetadataUnlessSpecified() throws Exception {
		E entity = service.getById(0);
		service.retire(entity, "something");
		Context.flushSession();

		List<E> entities = service.getByNameFragment("t", false);
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount() - 1, entities.size());

		entities = service.getByNameFragment("t", true);
		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies return metadata that start with the specified name
	 * @see IMetadataDataService#getByNameFragment(String, boolean)
	 */
	@Test
	public void getByNameFragment_shouldReturnMetadataThatStartWithTheSpecifiedName() throws Exception {
		E entity = service.getById(0);

		// Search using the first four characters in the name
		List<E> entities = service.getByNameFragment(entity.getName(), false);
		Assert.assertTrue(entities.size() > 0);

		// Make sure the entity is in the results
		E found = null;
		for (E result : entities) {
			if (result.getId().equals(entity.getId())) {
				found = result;
				break;
			}
		}

		Assert.assertNotNull("Could not find entity in search results", found);
	}

	/**
	 * @verifies return all specified metadata records if paging is null
	 * @see IMetadataDataService#getByNameFragment(String, boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getByNameFragment_shouldReturnAllSpecifiedMetadataRecordsIfPagingIsNull() throws Exception {
		E entity = service.getById(0);

		// This assumes that the entity name is unique
		List<E> entities = service.getByNameFragment(entity.getName(), false, null);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		assertEntity(entity, entities.get(0));
	}

	/**
	 * @verifies return all specified metadata records if paging page or size is less than one
	 * @see IMetadataDataService#getByNameFragment(String, boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getByNameFragment_shouldReturnAllSpecifiedMetadataRecordsIfPagingPageOrSizeIsLessThanOne() throws Exception {
		E entity = service.getById(0);

		PagingInfo paging = new PagingInfo(0, 1);
		// This assumes that the entity name is unique
		List<E> entities = service.getByNameFragment(entity.getName(), false, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		assertEntity(entity, entities.get(0));

		paging = new PagingInfo(1, 0);
		entities = service.getByNameFragment(entity.getName(), false, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		assertEntity(entity, entities.get(0));
	}

	/**
	 * @verifies set the paging total records to the total number of metadata records
	 * @see IMetadataDataService#getByNameFragment(String, boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getByNameFragment_shouldSetThePagingTotalRecordsToTheTotalNumberOfMetadataRecords() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities = service.getByNameFragment("T", false, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals(Long.valueOf(getTestEntityCount()), paging.getTotalRecordCount());
	}

	/**
	 * @verifies not get the total paging record count if it is more than zero
	 * @see IMetadataDataService#getByNameFragment(String, boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getByNameFragment_shouldNotGetTheTotalPagingRecordCountIfItIsMoreThanZero() throws Exception {
		E entity = service.getById(0);
		PagingInfo paging = new PagingInfo(1, 1);

		// First check that the full total is set
		List<E> entities = service.getByNameFragment(entity.getName(), false, paging);
		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals((Long)1L, paging.getTotalRecordCount());

		// Now manually set the total and check that it is not reset
		paging = new PagingInfo(1, 1);
		paging.setTotalRecordCount(10L);

		entities = service.getByNameFragment(entity.getName(), false, paging);
		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals((Long)10L, paging.getTotalRecordCount());

		// Finally, explicitly set the paging to not load the total and make sure it is not counted
		paging = new PagingInfo(1, 1);
		paging.setLoadRecordCount(false);

		entities = service.getByNameFragment(entity.getName(), false, paging);
		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertNull(paging.getTotalRecordCount());
	}

	/**
	 * @verifies return paged metadata records if paging is specified
	 * @see IMetadataDataService#getByNameFragment(String, boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getByNameFragment_shouldReturnPagedMetadataRecordsIfPagingIsSpecified() throws Exception {
		List<E> allEntities = service.getByNameFragment("T", false);

		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities;

		for (int i = 0; i < getTestEntityCount(); i++) {
			paging.setPage(i + 1);
			entities = service.getByNameFragment("T", false, paging);

			Assert.assertNotNull(entities);
			Assert.assertEquals(1, entities.size());
			Assert.assertEquals(Long.valueOf(getTestEntityCount()), paging.getTotalRecordCount());

			assertEntity(allEntities.get(i), entities.get(0));
		}
	}

	/**
	 * @verifies return all specified metadata records if paging is null
	 * @see IMetadataDataService#getAll(boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnAllSpecifiedMetadataRecordsIfPagingIsNull() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		List<E> entities = service.getAll(true, null);

		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies return all specified metadata records if paging page or size is less than one
	 * @see IMetadataDataService#getAll(boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnAllSpecifiedMetadataRecordsIfPagingPageOrSizeIsLessThanOne() throws Exception {
		String reason = "test retire";
		E entity = service.getById(0);
		service.retire(entity, reason);

		Context.flushSession();

		PagingInfo paging = new PagingInfo(0, 1);
		List<E> entities = service.getAll(true, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());

		paging = new PagingInfo(1, 0);
		entities = service.getAll(true, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(getTestEntityCount(), entities.size());
	}

	/**
	 * @verifies set the paging total records to the total number of metadata records
	 * @see IMetadataDataService#getAll(boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldSetThePagingTotalRecordsToTheTotalNumberOfMetadataRecords() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities = service.getAll(false, paging);

		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals(Long.valueOf(getTestEntityCount()), paging.getTotalRecordCount());
	}

	/**
	 * @verifies not get the total paging record count if it is more than zero
	 * @see IMetadataDataService#getAll(boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldNotGetTheTotalPagingRecordCountIfItIsMoreThanZero() throws Exception {
		PagingInfo paging = new PagingInfo(1, 1);

		// First check that the full total is set
		List<E> entities = service.getAll(false, paging);
		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals(Long.valueOf(getTestEntityCount()), paging.getTotalRecordCount());

		// Now manually set the total and check that it is not reset
		paging = new PagingInfo(1, 1);
		paging.setTotalRecordCount(10L);

		entities = service.getAll(false, paging);
		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals((Long)10L, paging.getTotalRecordCount());

		// Finally, explicitly set the paging to not load the total and make sure it is not counted
		paging = new PagingInfo(1, 1);
		paging.setLoadRecordCount(false);

		entities = service.getAll(false, paging);
		Assert.assertNotNull(entities);
		Assert.assertEquals(1, entities.size());
		Assert.assertNull(paging.getTotalRecordCount());
	}

	/**
	 * @verifies return paged metadata records if paging is specified
	 * @see IMetadataDataService#getAll(boolean, org.openmrs.module.openhmis.commons.api.PagingInfo)
	 */
	@Test
	public void getAll_shouldReturnPagedMetadataRecordsIfPagingIsSpecified() throws Exception {
		List<E> allEntities = service.getAll();

		PagingInfo paging = new PagingInfo(1, 1);
		List<E> entities;

		for (int i = 0; i < getTestEntityCount(); i++) {
			paging.setPage(i + 1);
			entities = service.getAll(paging);

			Assert.assertNotNull(entities);
			Assert.assertEquals(1, entities.size());
			assertEntity(allEntities.get(i), entities.get(0));
		}
	}
}
