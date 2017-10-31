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
package org.openmrs.module.openhmis.commons.fragment.controller;

import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.ModuleUtil;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 * Fragment controller for the loadReusableModules.gsp fragment.
 */
public class LoadReusableModulesFragmentController {
	/**
	 * The module id for the UI Framework mod.
	 */
	public static final String UI_FRAMEWORK_MODULE_ID = "uiframework";

	/**
	 * Adds the angular version as an model attribute and renders the fragment.
	 */
	public void controller(FragmentModel model) {
		Module module = ModuleFactory.getModuleById(UI_FRAMEWORK_MODULE_ID);

		String angularVersion;
		if (module == null) {
			angularVersion = "0.0";
		} else {
			String currentVersion = module.getVersion();

			if (ModuleUtil.compareVersion(currentVersion, "3.9") >= 0) {
				angularVersion = "1.5";
			} else {
				angularVersion = "1.3";
			}
		}

		model.addAttribute("angularVersion", angularVersion);
	}
}
