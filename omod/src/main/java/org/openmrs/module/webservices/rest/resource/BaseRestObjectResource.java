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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IObjectDataService;
import org.openmrs.module.openhmis.commons.api.exception.PrivilegeException;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

/**
 * REST resource for {@link org.openmrs.OpenmrsObject} entities.
 * @param <E> The model class
 */
public abstract class BaseRestObjectResource<E extends OpenmrsObject> extends DelegatingCrudResource<E>
        implements IObjectDataServiceResource<E, IObjectDataService<E>> {
	private static final Log LOG = LogFactory.getLog(BaseRestObjectResource.class);

	private Class<E> entityClass = null;

	@Override
	public abstract E newDelegate();

	@Override
	public abstract Class<? extends IObjectDataService<E>> getServiceClass();

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");

		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = getRepresentationDescription(new DefaultRepresentation());
		description.removeProperty("uuid");

		return description;
	}

	@Override
	public E save(E delegate) {
		Class<? extends IObjectDataService<E>> clazz = getServiceClass();
		if (clazz == null) {
			throw new IllegalStateException("This resource has not be defined to allow saving.  "
			        + "To save, implement the resource getServiceClass method.");
		}

		IObjectDataService<E> service = Context.getService(clazz);
		service.save(delegate);

		return delegate;
	}

	@Override
	public E getByUniqueId(String uniqueId) {
		Class<? extends IObjectDataService<E>> clazz = getServiceClass();
		if (clazz == null) {
			throw new IllegalStateException("This resource has not be defined to allow searching. "
			        + "To search, implement the resource getServiceClass method.");
		}

		E result = null;

		// Ensure that a service is found for this resource. This is a fix for changes to the RESTWS module as of v2.12.
		IObjectDataService<E> service = getService();
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

	@Override
	public void delete(E delegate, String reason, RequestContext context) {
		purge(delegate, context);
	}

	@Override
	public void purge(E delegate, RequestContext context) {
		Class<? extends IObjectDataService<E>> clazz = getServiceClass();
		if (clazz == null) {
			throw new IllegalStateException("This resource has not be defined to allow purging. "
			        + "To purge, implement the resource getServiceClass method.");
		}

		IObjectDataService<E> service = Context.getService(clazz);
		service.purge(delegate);
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) {
		Class<? extends IObjectDataService<E>> clazz = getServiceClass();
		if (clazz == null) {
			throw new IllegalStateException("This resource has not be defined to allow searching. "
			        + "To search, implement the resource getServiceClass method.");
		}

		IObjectDataService<E> service = Context.getService(clazz);
		PagingInfo pagingInfo = PagingUtil.getPagingInfoFromContext(context);
		return new AlreadyPagedWithLength<E>(context, service.getAll(pagingInfo), pagingInfo.hasMoreResults(),
		        pagingInfo.getTotalRecordCount());
	}

	protected IObjectDataService<E> getService() {
		// Ensure that the service class is not null. This is a fix for changes to the RESTWS module as of v2.12.
		Class<? extends IObjectDataService<E>> cls = getServiceClass();

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
