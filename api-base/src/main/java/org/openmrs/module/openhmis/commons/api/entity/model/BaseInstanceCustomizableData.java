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
 * Base class for {@link org.openmrs.OpenmrsData} models that can be customized based on an
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType}
 * @param <TInstanceType> The model instance type class.
 * @param <TAttribute> The model attribute class.
 */
public abstract class BaseInstanceCustomizableData<
			TInstanceType extends IInstanceType<?>,
			TAttribute extends IInstanceAttribute<?, ?, ?>>
		extends BaseCustomizableData<TAttribute>
		implements IInstanceCustomizable<TInstanceType, TAttribute> {
// @formatter:on
	public static final long serialVersionUID = 1L;

	private TInstanceType instanceType;

	@Override
	@SuppressWarnings("unchecked")
	protected void onAddAttribute(TAttribute attribute) {
		super.onAddAttribute(attribute);

		((IInstanceAttribute)attribute).setOwner(this);
	}

	@Override
	protected void onRemoveAttribute(TAttribute attribute) {
		super.onRemoveAttribute(attribute);

		attribute.setOwner(null);
	}

	@Override
	public TInstanceType getInstanceType() {
		return instanceType;
	}

	@Override
	public void setInstanceType(TInstanceType instanceType) {
		this.instanceType = instanceType;
	}
}
