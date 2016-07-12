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
package org.openmrs.module.openhmis.commons.web;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.IObjectDataService;

/**
 * Support class to build property editors for entities.
 * @param <E> The model class
 */
public class EntityPropertyEditor<E extends OpenmrsObject> extends PropertyEditorSupport {
	private IObjectDataService<E> service;

	public EntityPropertyEditor(Class<? extends IObjectDataService<E>> service) {
		this.service = Context.getService(service);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getAsText() {
		E entity = (E)getValue();

		if (entity == null) {
			return "";
		} else {
			return entity.getId().toString();
		}
	}

	@Override
	public void setAsText(String text) {
		if (StringUtils.isEmpty(text)) {
			setValue(null);
		} else {
			E entity;
			if (NumberUtils.isNumber(text)) {
				entity = service.getById(Integer.valueOf(text));
			} else {
				entity = service.getByUuid(text);
			}

			setValue(entity);
			if (entity == null) {
				throw new IllegalArgumentException("Entity ('" + getEntityClass().getName() + "') not found: " + text);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected Class<E> getEntityClass() {
		ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();

		return (Class)parameterizedType.getActualTypeArguments()[0];
	}
}
