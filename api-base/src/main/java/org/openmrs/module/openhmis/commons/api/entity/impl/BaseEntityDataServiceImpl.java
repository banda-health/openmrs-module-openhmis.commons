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
package org.openmrs.module.openhmis.commons.api.entity.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.OpenmrsData;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataService;
import org.openmrs.module.openhmis.commons.api.entity.security.IEntityAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.openhmis.commons.api.util.PrivilegeUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * The base type for {@link org.openmrs.module.openhmis.commons.api.entity.IEntityDataService}s.
 * @param <E> The entity model type.
 */
@Transactional
public abstract class BaseEntityDataServiceImpl<E extends OpenmrsData>
        extends BaseObjectDataServiceImpl<E, IEntityAuthorizationPrivileges> implements IEntityDataService<E> {
	@Override
	@Transactional
	public E voidEntity(E entity, final String reason) {
		IEntityAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getVoidPrivilege())) {
			PrivilegeUtil.requirePrivileges(Context.getAuthenticatedUser(), privileges.getVoidPrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to void cannot be null.");
		}
		if (StringUtils.isEmpty(reason)) {
			throw new IllegalArgumentException("The reason to void must be defined.");
		}

		final User user = Context.getAuthenticatedUser();
		final Date dateVoided = new Date();
		setVoidProperties(entity, reason, user, dateVoided);

		List<OpenmrsData> updatedObjects = executeOnRelatedObjects(OpenmrsData.class, entity, new Action1<OpenmrsData>() {
			@Override
			public void apply(OpenmrsData data) {
				setVoidProperties(data, reason, user, dateVoided);
			}
		});

		if (!updatedObjects.isEmpty()) {
			return saveAll(entity, updatedObjects);
		} else {
			return save(entity);
		}
	}

	protected void setVoidProperties(OpenmrsData data, String reason, User user, Date dateVoided) {
		data.setVoided(true);
		data.setVoidReason(reason);
		data.setVoidedBy(user);
		data.setDateVoided(dateVoided);
	}

	@Override
	@Transactional
	public E unvoidEntity(E entity) {
		IEntityAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getVoidPrivilege())) {
			PrivilegeUtil.requirePrivileges(Context.getAuthenticatedUser(), privileges.getVoidPrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to unvoid cannot be null.");
		}

		setUnvoidProperties(entity);

		List<OpenmrsData> updatedObjects = executeOnRelatedObjects(OpenmrsData.class, entity, new Action1<OpenmrsData>() {
			@Override
			public void apply(OpenmrsData data) {
				setUnvoidProperties(data);
			}
		});

		if (!updatedObjects.isEmpty()) {
			return saveAll(entity, updatedObjects);
		} else {
			return save(entity);
		}
	}

	protected void setUnvoidProperties(OpenmrsData data) {
		data.setVoided(false);
		data.setVoidReason(null);
		data.setVoidedBy(null);
	}

	/**
	 * Gets all unvoided entities.
	 * @param pagingInfo
	 * @return Returns all unvoided entities
	 * @should return all unvoided entities when voided is not specified
	 */
	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(PagingInfo pagingInfo) {
		return getAll(false, pagingInfo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(boolean includeVoided) {
		return getAll(includeVoided, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(final boolean includeVoided, PagingInfo pagingInfo) {
		IEntityAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			PrivilegeUtil.requirePrivileges(Context.getAuthenticatedUser(), privileges.getGetPrivilege());
		}

		return executeCriteria(getEntityClass(), pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				if (!includeVoided) {
					criteria.add(Restrictions.eq("voided", false));
				}
			}
		}, getDefaultSort());
	}
}
