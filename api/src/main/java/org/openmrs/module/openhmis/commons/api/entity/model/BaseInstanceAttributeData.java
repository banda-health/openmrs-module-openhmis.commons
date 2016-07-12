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

// @formatter:off
/**
 * Base class for {@link org.openmrs.OpenmrsData} instance attribute models.
 * @param <TOwner> The class of the owning model.
 * @param <TAttributeType> The class of the attribute type.
 */
public abstract class BaseInstanceAttributeData<
			TOwner extends IInstanceCustomizable<TInstanceType, ?>,
			TInstanceType extends IInstanceType<TAttributeType>,
			TAttributeType extends IInstanceAttributeType<TInstanceType>>
		extends BaseAttributeData<TOwner, TAttributeType>
		implements IInstanceAttribute<TOwner, TInstanceType, TAttributeType> {
// @formatter:on
	public static final long serialVersionUID = 1L;
}
