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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.OpenmrsData;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataService;
import org.openmrs.module.openhmis.commons.api.exception.PrivilegeException;
import org.openmrs.module.openhmis.commons.api.f.Action2;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.springframework.beans.BeanUtils;

/**
 * REST resource for {@link org.openmrs.OpenmrsData} entities.
 * @param <E> The model class
 */
public abstract class BaseRestDataResource<E extends OpenmrsData> extends DataDelegatingCrudResource<E>
        implements IEntityDataServiceResource<E> {
	private static final Log LOG = LogFactory.getLog(BaseRestDataResource.class);

	private Class<E> entityClass = null;

	/**
	 * Syncs the base collection with the items in the sync collection. This will add any missing items, updating existing
	 * items, and delete any items not found in the sync collection.
	 * @param base The collection to update.
	 * @param sync The collection used to update the base.
	 * @param <E> The {@link OpenmrsObject} stored in the collection.
	 */
	public static <E extends OpenmrsObject> void syncCollection(Collection<E> base, Collection<E> sync) {
		syncCollection(base, sync, new Action2<Collection<E>, E>() {
			@Override
			public void apply(Collection<E> collection, E entity) {
				collection.add(entity);
			}
		}, new Action2<Collection<E>, E>() {
			@Override
			public void apply(Collection<E> collection, E entity) {
				collection.remove(entity);
			}
		});
	}

	public static <E extends OpenmrsObject> void syncCollection(Collection<E> base, Collection<E> sync,
	        Action2<Collection<E>, E> add, Action2<Collection<E>, E> remove) {
		Map<String, E> baseMap = new HashMap<String, E>(base.size());
		Map<String, E> syncMap = new HashMap<String, E>(sync.size());
		for (E item : base) {
			baseMap.put(item.getUuid(), item);
		}
		for (E item : sync) {
			syncMap.put(item.getUuid(), item);
		}

		for (E item : baseMap.values()) {
			if (syncMap.containsKey(item.getUuid())) {
				// Update the existing item
				E syncItem = syncMap.get(item.getUuid());
				syncItem.setId(item.getId());

				BeanUtils.copyProperties(syncItem, item);
			} else {
				// Delete item that is not in the sync collection
				remove.apply(base, item);
			}
		}

		for (E item : syncMap.values()) {
			if (!baseMap.containsKey(item.getUuid())) {
				// Add the item not in the base collection
				add.apply(base, item);
			}
		}
	}

	@Override
	public abstract E newDelegate();

	@Override
	public abstract Class<? extends IEntityDataService<E>> getServiceClass();

	@Override
	public E save(E delegate) {
		return getService().save(delegate);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");
		description.addProperty("display", findMethod("getDisplayString"));

		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("voided");
			description.addProperty("voidReason");

			if (rep instanceof FullRepresentation) {
				description.addProperty("auditInfo", findMethod("getAuditInfo"));
			}
		}

		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = getRepresentationDescription(new DefaultRepresentation());
		description.removeProperty("uuid");
		description.removeProperty("voidReason");

		return description;
	}

	public String getDisplayString(E instance) {
		return instance.getClass().getSimpleName();
	}

	@Override
	public E getByUniqueId(String uniqueId) {
		if (StringUtils.isEmpty(uniqueId)) {
			return null;
		}

		E result = null;

		// Ensure that a service is found for this resource. This is a fix for changes to the RESTWS module as of v2.12.
		IEntityDataService<E> service = getService();
		if (service != null) {
			try {
				result = service.getByUuid(uniqueId);
			} catch (PrivilegeException p) {
				LOG.error("Exception occured when trying to get entity with ID <" + uniqueId + "> as privilege is missing",
				    p);
				throw new PrivilegeException("Can't get entity with ID <" + uniqueId + "> as privilege is missing");
			}
		}

		return result;
	}

	@Override
	protected void delete(E delegate, String reason, RequestContext context) {
		getService().voidEntity(delegate, reason);
	}

	@Override
	public void purge(E delegate, RequestContext context) {
		getService().purge(delegate);
	}

	@Override
	protected NeedsPaging<E> doGetAll(RequestContext context) {
		return new NeedsPaging<E>(getService().getAll(), context);
	}

	@Override
	protected AlreadyPaged<E> doSearch(RequestContext context) {
		String query = context.getRequest().getParameter("q");

		return new ServiceSearcher<E>(this.getServiceClass(), "getResources", "getCountOfResources").search(query, context);
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

	protected IEntityDataService<E> getService() {
		// Ensure that the service class is not null. This is a fix for changes to the RESTWS module as of v2.12.
		Class<? extends IEntityDataService<E>> cls = getServiceClass();

		if (cls == null) {
			return null;
		} else {
			return Context.getService(cls);
		}
	}
}
