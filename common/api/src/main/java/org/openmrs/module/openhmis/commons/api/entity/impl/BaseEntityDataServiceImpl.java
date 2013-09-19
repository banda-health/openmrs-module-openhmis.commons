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
import org.openmrs.OpenmrsObject;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.Utility;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataService;
import org.openmrs.module.openhmis.commons.api.entity.security.IEntityAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The base type for {@link org.openmrs.module.openhmis.commons.api.entity.IEntityDataService}s.
 * @param <E> The entity model type.
 */
@Transactional
public abstract class BaseEntityDataServiceImpl<E extends OpenmrsData>
		extends BaseObjectDataServiceImpl<E, IEntityAuthorizationPrivileges>
		implements IEntityDataService<E> {
	@Override
	@Transactional
	public E voidEntity(E entity, String reason) {
		IEntityAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getVoidPrivilege())) {
			Context.requirePrivilege(privileges.getVoidPrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to void cannot be null.");
		}
		if (StringUtils.isEmpty(reason)) {
			throw new IllegalArgumentException("The reason to void must be defined.");
		}

		User user = Context.getAuthenticatedUser();
		Date dateVoided = new Date();
		setVoidProperties(entity, reason, user, dateVoided);

		Collection<? extends OpenmrsObject> relatedObjects = getRelatedObjects(entity);
		List<OpenmrsData> updatedObjects = new ArrayList<OpenmrsData>();
		if (relatedObjects != null && relatedObjects.size() > 0) {
			for (OpenmrsObject object : relatedObjects) {
				OpenmrsData data = Utility.as(OpenmrsData.class, object);
				if (data != null) {
					setVoidProperties(data, reason, user, dateVoided);

					updatedObjects.add(data);
				}
			}
		}

		if (updatedObjects.size() > 0) {
			return saveAll(entity, relatedObjects);
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
	public E unvoidEntity(E entity) throws APIException {
		IEntityAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getVoidPrivilege())) {
			Context.requirePrivilege(privileges.getVoidPrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to unvoid cannot be null.");
		}

		setUnvoidProperties(entity);

		Collection<? extends OpenmrsObject> relatedObjects = getRelatedObjects(entity);
		List<OpenmrsData> updatedObjects = new ArrayList<OpenmrsData>();
		if (relatedObjects != null && relatedObjects.size() > 0) {
			for (OpenmrsObject object : relatedObjects) {
				OpenmrsData data = Utility.as(OpenmrsData.class, object);
				if (data != null) {
					setUnvoidProperties(data);

					updatedObjects.add(data);
				}
			}
		}

		if (updatedObjects.size() > 0) {
			return saveAll(entity, relatedObjects);
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
	 * @throws APIException
	 * @should return all unvoided entities when voided is not specified
	 */
	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(PagingInfo pagingInfo) throws APIException {
		return getAll(false, pagingInfo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(boolean includeVoided) throws APIException {
		return getAll(includeVoided, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(final boolean includeVoided, PagingInfo pagingInfo) throws APIException {
		IEntityAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		List<E> results = executeCriteria(getEntityClass(), pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				if (!includeVoided) {
					criteria.add(Restrictions.eq("voided", false));
				}
			}
		});
		return sort(results);
	}
}
