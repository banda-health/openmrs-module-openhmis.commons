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

import org.openmrs.OpenmrsObject;
import org.openmrs.module.openhmis.commons.api.entity.IObjectDataService;

/**
 * Represents REST resources for {@link org.openmrs.OpenmrsObject} models
 * @param <TEntity> The model class
 * @param <TService> The data service class
 */
public interface IObjectDataServiceResource<TEntity extends OpenmrsObject, TService extends IObjectDataService<TEntity>> {
	Class<? extends TService> getServiceClass();

	Class<TEntity> getEntityClass();
}
