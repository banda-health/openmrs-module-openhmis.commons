/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
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

import java.util.List;
import java.util.Vector;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.api.APIException;

public abstract class BaseCustomizableInstanceType<TInstanceType extends BaseCustomizableInstanceType<TInstanceType, AT>, AT extends InstanceAttributeType<TInstanceType>>
		extends BaseOpenmrsMetadata
		implements InstanceType<TInstanceType, AT> {
	private Integer customizableInstanceTypeId;
	private List<AT> attributeTypes;
	
	public AT addAttributeType(String name, String format, Integer foreignKey, String regExp, boolean required, Integer attributeOrder) {
		AT attributeType = getAttributeTypeInstance();

		attributeType.setOwner((TInstanceType) this);

		attributeType.setName(name);
		attributeType.setFormat(format);
		attributeType.setForeignKey(foreignKey);
		attributeType.setRequired(required);
		
		addAttributeType(attributeOrder, attributeType);

		return attributeType;
	}

	public void addAttributeType(AT attributeType) {
		addAttributeType(null, attributeType);
	}

	public void addAttributeType(Integer index, AT attributeType) {
		if (attributeType == null) {
			throw new NullPointerException("The payment mode attribute type to add must be defined.");
		}

		if (attributeType.getOwner() != this) {
			attributeType.setOwner((TInstanceType) this);
		}

		if (this.attributeTypes == null) {
			this.attributeTypes = new Vector<AT>();
		}
		
		if (index == null) {
			attributeType.setAttributeOrder(getAttributeTypes().size());
			getAttributeTypes().add(attributeType);
		}
		else {
			if (index > getAttributeTypes().size())
				throw new APIException("Invalid attribute order. Should not leave space in the list (list length: " + getAttributeTypes().size() + ", index given: " + index + ")." );
			attributeType.setAttributeOrder(index);
			getAttributeTypes().add(index, attributeType);
		}
		
		this.attributeTypes.add(attributeType);
	}

	public void removeAttributeType(InstanceAttributeType<TInstanceType> attributeType) {
		if (attributeType != null && this.attributeTypes != null) {
			this.attributeTypes.remove(attributeType);
		}
	}

	// Getters & setters
	@Override
	public Integer getId() {
		return customizableInstanceTypeId;
	}

	@Override
	public void setId(Integer id) {
		customizableInstanceTypeId = id;
	}

	public List<AT> getAttributeTypes() {
		return attributeTypes;
	}

	public void setAttributeTypes(List<AT> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}
}
