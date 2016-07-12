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
package org.openmrs.module.openhmis.commons.api.entity.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openmrs.BaseOpenmrsMetadata;

/**
 * Base class for {@link org.openmrs.OpenmrsMetadata} that need to support JSON serialization.
 */
public abstract class BaseSerializableOpenmrsMetadata extends BaseOpenmrsMetadata {
	public static final long serialVersionUID = 0L;

	@Override
	@JsonIgnore
	public Boolean getRetired() {
		return isRetired();
	}
}
