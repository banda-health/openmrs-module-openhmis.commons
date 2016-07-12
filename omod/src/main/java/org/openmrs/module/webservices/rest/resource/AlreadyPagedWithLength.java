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

import java.util.List;

import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * Represents paged results that include a total length.
 * @param <T> The model class.
 */
public class AlreadyPagedWithLength<T> extends AlreadyPaged<T> {
	private long length = 0;

	public AlreadyPagedWithLength(RequestContext context, List<T> results, boolean hasMoreResults, long length) {
		super(context, results, hasMoreResults);
		this.length = length;
	}

	@Override
	public SimpleObject toSimpleObject(Converter converter) {
		SimpleObject obj = super.toSimpleObject(converter);
		obj.add("length", this.length);
		return obj;
	}
}
