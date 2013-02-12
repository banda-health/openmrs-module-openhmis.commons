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
package org.openmrs.module.openhmis.commons.api.entity.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.OpenmrsData;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataAccessor;
import org.openmrs.module.openhmis.commons.api.entity.security.IDataAuthorizationPrivileges;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * The base type for {@link org.openmrs.module.openhmis.commons.api.entity.IEntityDataAccessor}s.
 * @param <E> The entity model type.
 */
@Transactional
public abstract class BaseEntityDataAccessorImpl<E extends OpenmrsData>
		extends BaseObjectDataAccessorImpl<E, IDataAuthorizationPrivileges> implements IEntityDataAccessor<E> {

	@Override
	@Transactional
	public E voidEntity(E entity, String reason) {
		IDataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getVoidPrivilege())) {
			Context.requirePrivilege(privileges.getVoidPrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to void cannot be null.");
		}
		if (StringUtils.isEmpty(reason)) {
			throw new IllegalArgumentException("The reason to void must be defined.");
		}

		entity.setVoided(true);
		entity.setVoidReason(reason);
		entity.setVoidedBy(Context.getAuthenticatedUser());
		entity.setDateVoided(new Date());

		return save(entity);
	}

	@Override
	@Transactional
	public E unvoidEntity(E entity) throws APIException {
		IDataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getVoidPrivilege())) {
			Context.requirePrivilege(privileges.getVoidPrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to unvoid cannot be null.");
		}

		entity.setVoided(false);
		entity.setVoidReason(null);
		entity.setVoidedBy(null);

		return save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(boolean includeVoided) throws APIException {
		return getAll(includeVoided, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(boolean includeVoided, PagingInfo pagingInfo) throws APIException {
		IDataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		Criteria criteria = repository.createCriteria(getEntityClass());
		if (!includeVoided) {
			criteria.add(Restrictions.eq("voided", false));
		}

		loadPagingTotal(pagingInfo, criteria);
		return repository.select(getEntityClass(), createPagingCriteria(pagingInfo, criteria));
	}
}
