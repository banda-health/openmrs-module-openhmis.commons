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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.openhmis.commons.api.util.PrivilegeUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * The base type for {@link org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService}s.
 * @param <E> The entity metadata model type.
 */
@Transactional
public abstract class BaseMetadataDataServiceImpl<E extends OpenmrsMetadata>
        extends BaseObjectDataServiceImpl<E, IMetadataAuthorizationPrivileges> implements IMetadataDataService<E> {
	protected static final int NAME_LENGTH = 255;

	@Override
	protected Order[] getDefaultSort() {
		// By default, use the name as the sorting column for metadata
		return new Order[] { Order.asc("name") };
	}

	@Override
	@Transactional
	public E retire(E entity, final String reason) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getRetirePrivilege())) {
			PrivilegeUtil.requirePrivileges(Context.getAuthenticatedUser(), privileges.getRetirePrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to retire cannot be null.");
		}
		if (StringUtils.isEmpty(reason)) {
			throw new IllegalArgumentException("The reason to retire must be defined.");
		}

		final User user = Context.getAuthenticatedUser();
		final Date dateRetired = new Date();
		setRetireProperties(entity, reason, user, dateRetired);

		List<OpenmrsMetadata> updatedObjects =
		        executeOnRelatedObjects(OpenmrsMetadata.class, entity, new Action1<OpenmrsMetadata>() {
			        @Override
			        public void apply(OpenmrsMetadata metadata) {
				        setRetireProperties(metadata, reason, user, dateRetired);
			        }
		        });
		if (!updatedObjects.isEmpty()) {
			return saveAll(entity, updatedObjects);
		} else {
			return save(entity);
		}
	}

	/**
	 * Sets the properties to retire an {@link OpenmrsMetadata} model object.
	 * @param metadata The object to retire.
	 * @param reason The reason to retire the metadata.
	 * @param user The user that is retiring the metadata.
	 * @param dateRetired The date that the metadata was retired.
	 */
	protected void setRetireProperties(OpenmrsMetadata metadata, String reason, User user, Date dateRetired) {
		metadata.setRetired(true);
		metadata.setRetireReason(reason);
		metadata.setRetiredBy(user);
		metadata.setDateRetired(dateRetired);
	}

	@Override
	@Transactional
	public E unretire(E entity) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getRetirePrivilege())) {
			PrivilegeUtil.requirePrivileges(Context.getAuthenticatedUser(), privileges.getRetirePrivilege());
		}

		if (entity == null) {
			throw new NullPointerException("The entity to unretire cannot be null.");
		}

		setUnretireProperties(entity);

		List<OpenmrsMetadata> updatedObjects =
		        executeOnRelatedObjects(OpenmrsMetadata.class, entity, new Action1<OpenmrsMetadata>() {
			        @Override
			        public void apply(OpenmrsMetadata metadata) {
				        setUnretireProperties(metadata);
			        }
		        });
		if (!updatedObjects.isEmpty()) {
			return saveAll(entity, updatedObjects);
		} else {
			return save(entity);
		}
	}

	protected void setUnretireProperties(OpenmrsMetadata metadata) {
		metadata.setRetired(false);
		metadata.setRetireReason(null);
		metadata.setRetiredBy(null);
	}

	/**
	 * Gets all unretired entites.
	 * @param pagingInfo
	 * @return Returns all unretired entities
	 * @should return all unretired entities when retired is not specified
	 */
	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(PagingInfo pagingInfo) {
		return getAll(false, pagingInfo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(boolean includeRetired) {
		return getAll(includeRetired, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(final boolean includeRetired, PagingInfo pagingInfo) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			PrivilegeUtil.requirePrivileges(Context.getAuthenticatedUser(), privileges.getGetPrivilege());
		}

		return executeCriteria(getEntityClass(), pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				if (!includeRetired) {
					criteria.add(Restrictions.eq("retired", false));
				}
			}
		}, getDefaultSort());
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getByNameFragment(String nameFragment, boolean includeRetired) {
		return getByNameFragment(nameFragment, includeRetired, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getByNameFragment(final String nameFragment, final boolean includeRetired, PagingInfo pagingInfo) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			PrivilegeUtil.requirePrivileges(Context.getAuthenticatedUser(), privileges.getGetPrivilege());
		}

		if (StringUtils.isEmpty(nameFragment)) {
			throw new IllegalArgumentException("The name fragment must be defined.");
		}
		if (nameFragment.length() > NAME_LENGTH) {
			throw new IllegalArgumentException("the name fragment must be less than 256 characters long.");
		}

		return executeCriteria(getEntityClass(), pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				criteria.add(Restrictions.ilike("name", nameFragment, MatchMode.START));

				if (!includeRetired) {
					criteria.add(Restrictions.eq("retired", false));
				}
			}
		}, getDefaultSort());
	}
}
