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

import org.openmrs.module.ModuleFactory;

/**
 * Utility class for working with modules.
 */
public class ModuleUtil {
	public static final String IDGEN_MODULE_ID = "idgen";

	public static Boolean isLoaded(String moduleId) {
		return ModuleFactory.getStartedModuleById(moduleId) != null;
	}
}
