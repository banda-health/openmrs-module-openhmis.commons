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

public abstract class IDataServiceTest<S extends IDataService<E>, E extends BaseOpenmrsData> extends IEntityServiceTest<S, E> {
	/**
	 * @verifies void the entity
	 * @see IDataService#voidEntity(org.openmrs.OpenmrsData, String)
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
	 * @see IDataService#voidEntity(org.openmrs.OpenmrsData, String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void voidEntity_shouldThrowIllegalArgumentExceptionWithNullReasonParameter() throws Exception {
		E entity = service.getById(0);

		service.voidEntity(entity, null);
	}

	/**
	 * @verifies throw NullPointerException with null entity
	 * @see IDataService#voidEntity(org.openmrs.OpenmrsData, String)
	 */
	@Test(expected = NullPointerException.class)
	public void voidEntity_shouldThrowNullPointerExceptionWithNullEntity() throws Exception {
		service.voidEntity(null, "something");
	}

	/**
	 * @verifies unvoid the entity
	 * @see IDataService#unvoidEntity(org.openmrs.OpenmrsData)
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
	 * @see IDataService#unvoidEntity(org.openmrs.OpenmrsData)
	 */
	@Test(expected = NullPointerException.class)
	public void unvoidEntity_shouldThrowNullPointerExceptionWithNullEntity() throws Exception {
		service.unvoidEntity(null);
	}
}
