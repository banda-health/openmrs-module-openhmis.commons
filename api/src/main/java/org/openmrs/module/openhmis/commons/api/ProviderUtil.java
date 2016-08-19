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
package org.openmrs.module.openhmis.commons.api;

import java.util.Collection;

import org.openmrs.Provider;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;

/**
 * Utility class for {@link org.openmrs.Provider}s.
 */
public class ProviderUtil {
	protected ProviderUtil() {}

	public static Provider getCurrentProvider() {
		return getCurrentProvider(Context.getProviderService());
	}

	public static Provider getCurrentProvider(ProviderService providerService) {
		Collection<Provider> providers = providerService.getProvidersByPerson(Context.getAuthenticatedUser().getPerson());
		if (!providers.isEmpty()) {
			return providers.iterator().next();
		}

		return null;
	}
}
