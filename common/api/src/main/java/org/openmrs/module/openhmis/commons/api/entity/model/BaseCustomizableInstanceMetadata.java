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

import org.openmrs.customdatatype.CustomValueDescriptor;

import java.util.Set;

public abstract class BaseCustomizableInstanceMetadata<TInstanceType extends IInstanceType, TAttribute extends IInstanceAttribute>
	extends BaseSerializableOpenmrsMetadata
	implements ICustomizableInstance<TInstanceType, TAttribute> {
	private Set<TAttribute> attributes;
	private TInstanceType instanceType;

	@Override
	public Set<TAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public Set<TAttribute> getActiveAttributes() {
		return BaseCustomizableInstanceObject.getActiveAttributes(this);
	}
	
	@Override
	public Set<TAttribute> getActiveAttributes(CustomValueDescriptor ofType) {
		return BaseCustomizableInstanceObject.getActiveAttributes(this, ofType);
	}

	@Override
	public void setAttributes(Set<TAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void addAttribute(TAttribute attribute) {
		BaseCustomizableInstanceObject.addAttribute(this, attribute);
	}

	@Override
	public void removeAttribute(TAttribute attribute) {
		BaseCustomizableInstanceObject.removeAttribute(this, attribute);
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

