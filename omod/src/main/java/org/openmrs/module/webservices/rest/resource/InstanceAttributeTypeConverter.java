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

import org.apache.commons.lang.NotImplementedException;
import org.hibernate.proxy.HibernateProxy;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;

/**
 * Resolves type names for {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType}s
 * @param <T> The instance attribute type class
 */
@Resource(name = RestConstants.VERSION_2 + "/openhmis/attributetype", supportedClass = IInstanceAttributeType.class,
        supportedOpenmrsVersions = { "1.8.*", "1.9.*", "1.10.*", "1.11.*", "1.12.*" })
public class InstanceAttributeTypeConverter<T extends IInstanceAttributeType<?>> extends MetadataDelegatingCrudResource<T> {
	private static final String NEED_SUBCLASS_HANDLER = "This operation should be handled by a subclass handler.";

	@Override
	public boolean hasTypesDefined() {
		return true;
	}

	/*TODO: This is a workaround for a possible bug in the REST module */
	@SuppressWarnings("unchecked")
	@Override
	protected String getTypeName(T delegate) {
		Class<? extends T> unproxiedClass = (Class<? extends T>)delegate.getClass();
		if (HibernateProxy.class.isAssignableFrom(unproxiedClass)) {
			unproxiedClass = (Class<? extends T>)unproxiedClass.getSuperclass();
		}

		return getTypeName((Class<? extends T>)unproxiedClass);
	}

	@Override
	public T newDelegate() {
		throw new NotImplementedException(NEED_SUBCLASS_HANDLER);
	}

	@Override
	public T save(T delegate) {
		throw new NotImplementedException(NEED_SUBCLASS_HANDLER);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		throw new NotImplementedException(NEED_SUBCLASS_HANDLER);
	}

	@Override
	public T getByUniqueId(String uniqueId) {
		throw new NotImplementedException(NEED_SUBCLASS_HANDLER);
	}

	@Override
	public void purge(T delegate, RequestContext context) {
		throw new NotImplementedException(NEED_SUBCLASS_HANDLER);
	}

}
