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

import org.openmrs.OpenmrsMetadata;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;

/**
 * REST search helper for {@link org.openmrs.OpenmrsMetadata}
 * @param <E> The model class
 */
public class MetadataSearcher<E extends OpenmrsMetadata> {
	private IMetadataDataService<E> service;

	public MetadataSearcher(Class<? extends IMetadataDataService<E>> serviceClass) {
		this.service = Context.getService(serviceClass);
	}

	public IMetadataDataService<E> getService() {
		return this.service;
	}

	public void setService(IMetadataDataService<E> service) {
		this.service = service;
	}

	/**
	 * Searches for entities using the specified name fragment.
	 * @param nameFragment The name search fragment
	 * @param context The request context
	 * @return The paged search results
	 */
	public AlreadyPaged<E> searchByName(String nameFragment, RequestContext context) {
		PagingInfo pagingInfo = PagingUtil.getPagingInfoFromContext(context);

		List<E> results = service.getByNameFragment(nameFragment, context.getIncludeAll(), pagingInfo);

		Boolean hasMoreResults = (pagingInfo.getPage() * pagingInfo.getPageSize()) < pagingInfo.getTotalRecordCount();
		return new AlreadyPagedWithLength<E>(context, results, hasMoreResults, pagingInfo.getTotalRecordCount());
	}

}
