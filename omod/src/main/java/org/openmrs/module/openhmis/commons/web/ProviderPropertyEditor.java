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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Provider;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;

/**
 * Property editor for {@link org.openmrs.Provider}s
 */
public class ProviderPropertyEditor extends PropertyEditorSupport {
	@Override
	public String getAsText() {
		Provider provider = (Provider)getValue();

		if (provider == null) {
			return "";
		} else {
			return provider.getId().toString();
		}
	}

	@Override
	public void setAsText(String text) {
		ProviderService service = Context.getProviderService();

		if (StringUtils.isEmpty(text)) {
			setValue(null);
		} else {
			Provider provider;
			if (NumberUtils.isNumber(text)) {
				provider = service.getProvider(Integer.valueOf(text));
			} else {
				provider = service.getProviderByUuid(text);
			}

			setValue(provider);
			if (provider == null) {
				throw new IllegalArgumentException("Provider not found: " + text);
			}
		}
	}
}
