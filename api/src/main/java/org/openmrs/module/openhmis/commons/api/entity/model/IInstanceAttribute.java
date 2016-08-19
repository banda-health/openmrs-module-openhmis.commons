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
 * Represents classes that define instance attribute data.
 * @param <TOwner> The parent {@link IInstanceCustomizable} class.
 * @param <TAttributeType> The {@link IInstanceAttributeType} class.
 */
public interface IInstanceAttribute<
			TOwner extends IInstanceCustomizable<TInstanceType, ?>,
			TInstanceType extends IInstanceType<TAttributeType>,
			TAttributeType extends IInstanceAttributeType<TInstanceType>>
		extends IAttribute<TOwner, TAttributeType> {
// @formatter:on
	TOwner getOwner();

	void setOwner(TOwner owner);
}
