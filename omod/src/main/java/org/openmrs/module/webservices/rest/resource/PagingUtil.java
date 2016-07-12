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

import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.webservices.rest.web.RequestContext;

/**
 * Utility class for extracting paging information from a request
 */
public class PagingUtil {
	private PagingUtil() {}

	public static PagingInfo getPagingInfoFromContext(RequestContext context) {
		Integer page = (context.getStartIndex() / context.getLimit()) + 1;
		return new PagingInfo(page, context.getLimit());
	}
}
