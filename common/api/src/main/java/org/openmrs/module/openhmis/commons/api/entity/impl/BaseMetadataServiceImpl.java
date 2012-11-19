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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataService;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * The base type for {@link IMetadataService}s.
 * @param <E> THe entity model type.
 */
@Transactional
public abstract class BaseMetadataServiceImpl<E extends BaseOpenmrsMetadata>
		extends BaseEntityServiceImpl<E, IMetadataAuthorizationPrivileges> implements IMetadataService<E> {

	@Override
	@Transactional
	public E retire(E entity, String reason) throws APIException {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getRetirePrivilege())) {
			Context.requirePrivilege(privileges.getRetirePrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to retire cannot be null.");
		}
		if (StringUtils.isEmpty(reason)) {
			throw new IllegalArgumentException("The reason to retire must be defined.");
		}

		entity.setRetired(true);
		entity.setRetireReason(reason);
		entity.setRetiredBy(Context.getAuthenticatedUser());
		entity.setDateRetired(new Date());

		return save(entity);
	}

	@Override
	@Transactional
	public E unretire(E entity) throws APIException {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getRetirePrivilege())) {
			Context.requirePrivilege(privileges.getRetirePrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to unretire cannot be null.");
		}

		entity.setRetired(false);
		entity.setRetireReason(null);
		entity.setRetiredBy(null);

		return save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(boolean includeRetired) throws APIException {
		return getAll(includeRetired, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(boolean includeRetired, PagingInfo pagingInfo) throws APIException {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		Criteria criteria = dao.createCriteria(getEntityClass());
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}

		loadPagingTotal(pagingInfo, criteria);
		return dao.select(getEntityClass(), createPagingCriteria(pagingInfo, criteria));
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> findByName(String nameFragment, boolean includeRetired) throws APIException {
		return findByName(nameFragment, includeRetired, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> findByName(String nameFragment, boolean includeRetired, PagingInfo pagingInfo) throws APIException {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		if (StringUtils.isEmpty(nameFragment)) {
			throw new IllegalArgumentException("The name fragment must be defined.");
		}
		if (nameFragment.length() > 255) {
			throw new IllegalArgumentException("the name fragment must be less than 256 characters long.");
		}

		Criteria criteria = dao.createCriteria(getEntityClass());
		criteria.add(Restrictions.ilike("name", nameFragment, MatchMode.START));

		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}

		loadPagingTotal(pagingInfo, criteria);
		return dao.select(getEntityClass(), createPagingCriteria(pagingInfo, criteria));
	}
}
