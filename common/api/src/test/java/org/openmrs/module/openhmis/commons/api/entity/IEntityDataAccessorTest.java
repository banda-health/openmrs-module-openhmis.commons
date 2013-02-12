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
import org.junit.Test;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.api.context.Context;

import java.util.Date;

public abstract class IEntityDataAccessorTest<S extends IEntityDataAccessor<E>, E extends BaseOpenmrsData> extends IObjectDataAccessorTest<S, E> {
	@Override
	protected void assertEntity(E expected, E actual) {
		super.assertEntity(expected, actual);

		Assert.assertEquals(expected.getChangedBy(), actual.getChangedBy());
		Assert.assertEquals(expected.getCreator(), actual.getCreator());
		Assert.assertEquals(expected.getDateChanged(), actual.getDateChanged());
		Assert.assertEquals(expected.getDateCreated(), actual.getDateCreated());
		Assert.assertEquals(expected.getVoided(), actual.getVoided());
		Assert.assertEquals(expected.getVoidedBy(), actual.getVoidedBy());
		Assert.assertEquals(expected.getVoidReason(), actual.getVoidReason());
		Assert.assertEquals(expected.getDateVoided(), actual.getDateVoided());
	}

	/**
	 * @verifies void the entity
	 * @see IEntityDataAccessor#voidEntity(org.openmrs.OpenmrsData, String)
	 */
	@Test
	public void voidEntity_shouldVoidTheEntity() throws Exception {
		String reason = "test void";
		E entity = service.getById(0);
		service.voidEntity(entity, reason);

		Context.flushSession();

		entity = service.getById(0);
		Assert.assertTrue(entity.getVoided());
		Assert.assertEquals(Context.getAuthenticatedUser(), entity.getVoidedBy());
		Assert.assertEquals(reason, entity.getVoidReason());
		Date now = new Date();
		Assert.assertTrue(entity.getDateVoided().before(now) || entity.getDateVoided().equals(now));
	}

	/**
	 * @verifies throw IllegalArgumentException with null reason parameter
	 * @see IEntityDataAccessor#voidEntity(org.openmrs.OpenmrsData, String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void voidEntity_shouldThrowIllegalArgumentExceptionWithNullReasonParameter() throws Exception {
		E entity = service.getById(0);

		service.voidEntity(entity, null);
	}

	/**
	 * @verifies throw NullPointerException with null entity
	 * @see IEntityDataAccessor#voidEntity(org.openmrs.OpenmrsData, String)
	 */
	@Test(expected = NullPointerException.class)
	public void voidEntity_shouldThrowNullPointerExceptionWithNullEntity() throws Exception {
		service.voidEntity(null, "something");
	}

	/**
	 * @verifies unvoid the entity
	 * @see IEntityDataAccessor#unvoidEntity(org.openmrs.OpenmrsData)
	 */
	@Test
	public void unvoidEntity_shouldUnvoidTheEntity() throws Exception {
		String reason = "test void";
		E entity = service.getById(0);
		service.voidEntity(entity, reason);

		Context.flushSession();

		entity = service.getById(0);
		Assert.assertTrue(entity.getVoided());

		service.unvoidEntity(entity);

		Context.flushSession();

		entity = service.getById(0);

		Assert.assertFalse(entity.getVoided());
		Assert.assertNull(entity.getVoidedBy());
		Assert.assertNull(entity.getVoidReason());
		Assert.assertNotNull(entity.getDateVoided());
	}

	/**
	 * @verifies throw NullPointerException with null entity
	 * @see IEntityDataAccessor#unvoidEntity(org.openmrs.OpenmrsData)
	 */
	@Test(expected = NullPointerException.class)
	public void unvoidEntity_shouldThrowNullPointerExceptionWithNullEntity() throws Exception {
		service.unvoidEntity(null);
	}
}
