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
package org.openmrs.module.webservices.rest.resource;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.commons.api.exception.PrivilegeException;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * REST resource for {@link org.openmrs.OpenmrsMetadata} entities.
 * @param <E> The model class
 */
public abstract class BaseRestMetadataResource<E extends OpenmrsMetadata> extends MetadataDelegatingCrudResource<E>
        implements IMetadataDataServiceResource<E> {

	private static final Log LOG = LogFactory.getLog(BaseRestMetadataResource.class);

	private Class<E> entityClass = null;

	/**
	 * Instantiates a new entity instance.
	 * @return The new instance
	 */
	@Override
	public abstract E newDelegate();

	@Override
	public abstract Class<? extends IMetadataDataService<E>> getServiceClass();

	/**
	 * Saves the entity.
	 * @param entity The entity to save
	 * @return The saved entity
	 */
	@Override
	public E save(E entity) {
		try {
			return getService().save(entity);
		} catch (PrivilegeException p) {
			LOG.error("Exception occured when trying to save entity <" + entity.getName() + "> as privilege is missing", p);
			throw new PrivilegeException("Can't save entity with name <" + entity.getName() + "> as privilege is missing");
		}
	}

	/**
	 * Gets the {@link DelegatingResourceDescription} for the specified {@link Representation}.
	 * @param rep The representation
	 * @return The resource description
	 */
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");
		description.addProperty("name");
		description.addProperty("description");
		description.addProperty("retired");

		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("retireReason");

			if (rep instanceof FullRepresentation) {
				description.addProperty("auditInfo", findMethod("getAuditInfo"));
			}
		}

		return description;
	}

	/**
	 * Gets a description of the properties that can be created.
	 * @return The resource description
	 */
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = getRepresentationDescription(new DefaultRepresentation());
		description.removeProperty("uuid");
		description.removeProperty("retireReason");

		return description;
	}

	/**
	 * Gets an entity by the UUID or {@code null} if not found.
	 * @param uniqueId The UUID for the entity
	 * @return The entity or null if not found
	 */
	@Override
	public E getByUniqueId(String uniqueId) {
		if (StringUtils.isEmpty(uniqueId)) {
			return null;
		}

		E result = null;

		// Ensure that a service is found for this resource. This is a fix for changes to the RESTWS module as of v2.12.
		IMetadataDataService<E> service = getService();
		if (service != null) {
			try {
				result = service.getByUuid(uniqueId);
			} catch (PrivilegeException p) {
				LOG.error("Exception occured when trying to get entity with ID <" + uniqueId
				        + "> as privilege is missing", p);
				throw new PrivilegeException("Can't get entity with ID <" + uniqueId + "> as privilege is missing");
			}
		}

		return result;
	}

	/**
	 * Purges the entity from the database.
	 * @param entity The entity to purge
	 * @param context The request context
	 */
	@Override
	public void purge(E entity, RequestContext context) {
		try {
			getService().purge(entity);
		} catch (PrivilegeException p) {
			LOG.error("Exception occured when trying to purge entity <" + entity.getName() + "> as privilege is missing", p);
			throw new PrivilegeException("Can't purge entity with name <" + entity.getName() + "> as privilege is missing");
		} catch (DataIntegrityViolationException e) {
			LOG.error("Exception occured when trying to purge entity <" + entity.getName() + ">", e);
			throw new DataIntegrityViolationException("Can't purge entity with name <" + entity.getName()
			        + "> as it is still in use");
		}
	}

	/**
	 * Gets all entities from the database using paging if specified in the context.
	 * @param context The request context
	 * @return A paged list of the entities
	 */
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		PagingInfo pagingInfo = PagingUtil.getPagingInfoFromContext(context);

		return new AlreadyPagedWithLength<E>(context, getService().getAll(context.getIncludeAll(), pagingInfo),
		        pagingInfo.hasMoreResults(), pagingInfo.getTotalRecordCount());
	}

	/**
	 * Finds all entities with a name that starts with the specified search query ('q' parameter).
	 * @param context The request context
	 * @return The paged results
	 */
	@Override
	protected PageableResult doSearch(RequestContext context) {
		context.setRepresentation(Representation.REF);
		String query = context.getParameter("q");

		return new MetadataSearcher<E>(getServiceClass()).searchByName(query, context);
	}

	/**
	 * Gets whether the specified entity is retired.
	 * @param entity The entity
	 * @return {@code true} if the entity is retired; otherwise, {@code false}
	 */
	@PropertyGetter("retired")
	public Boolean getRetired(E entity) {
		return entity.isRetired();
	}

	/**
	 * Gets the entity data service for this resource.
	 * @return The entity data service
	 */
	protected IMetadataDataService<E> getService() {
		// Ensure that the service class is not null. This is a fix for changes to the RESTWS module as of v2.12.
		Class<? extends IMetadataDataService<E>> cls = getServiceClass();

		if (cls == null) {
			return null;
		} else {
			return Context.getService(cls);
		}
	}

	/**
	 * Gets a usable instance of the actual class of the generic type E defined by the implementing sub-class.
	 * @return The class object for the entity.
	 */
	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		if (entityClass == null) {
			ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();

			entityClass = (Class)parameterizedType.getActualTypeArguments()[0];
		}

		return entityClass;
	}
}
