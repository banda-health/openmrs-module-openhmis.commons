/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.util;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.openhmis.commons.api.entity.model.SafeIdentifierSource;

/**
 * Idgen Utility class that does not directly reference the idgen module.
 */
public class SafeIdgenUtil {
	protected SafeIdgenUtil() {}

	/**
	 * Gets the identifier source information with the id in the specified global property.
	 * @param propertyName The global property name.
	 * @return The identifier source information or {@code null} if not defined.
	 */
	public static SafeIdentifierSource getIdentifierSourceInfo(String propertyName) {
		SafeIdentifierSource result = null;

		IdentifierSource source = IdgenUtil.getIdentifierSource(propertyName);
		if (source != null) {
			result = new SafeIdentifierSource(source.getId(), source.getUuid(), source.getName());
		}

		return result;
	}

	/**
	 * Gets the information for all defined identifier sources.
	 * @return A list containing the source information.
	 */
	public static List<SafeIdentifierSource> getAllIdentifierSourceInfo() {
		List<SafeIdentifierSource> results = new ArrayList<SafeIdentifierSource>();

		IdentifierSourceService service = Context.getService(IdentifierSourceService.class);
		List<IdentifierSource> sources = service.getAllIdentifierSources(false);
		if (sources != null && !sources.isEmpty()) {
			for (IdentifierSource source : sources) {
				results.add(new SafeIdentifierSource(source.getId(), source.getUuid(), source.getName()));
			}
		}

		return results;
	}
}
