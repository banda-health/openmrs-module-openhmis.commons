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

import org.openmrs.api.APIException;

import java.util.List;
import java.util.Vector;

@SuppressWarnings("unchecked")
public abstract class BaseCustomizableInstanceType<AT extends IInstanceAttributeType>
		extends BaseSerializableOpenmrsMetadata
		implements IInstanceType<AT> {
	private Integer customizableInstanceTypeId;
	private List<AT> attributeTypes;

	@Override
	public void addAttributeType(AT attributeType) {
		addAttributeType(null, attributeType);
	}

	@Override
	public void addAttributeType(Integer index, AT attributeType) {
		if (attributeType == null) {
			throw new NullPointerException("The payment mode attribute type to add must be defined.");
		}

		if (attributeType.getOwner() != this) {
			// Note that this may cause issues if the attribute type class does not have this class as the owner class.
			//  I'm not sure how to make generics check this at compile-time, I tried with self-bounded generic parameters
			//  but could never get it working.  Such is life.
			attributeType.setOwner(this);
		}

		if (this.attributeTypes == null) {
			this.attributeTypes = new Vector<AT>();
		}
		
		if (index == null) {
			attributeType.setAttributeOrder(getAttributeTypes().size());
			getAttributeTypes().add(attributeType);
		} else {
			if (index > getAttributeTypes().size()) {
				throw new APIException("Invalid attribute order. Should not leave space in the list (list length: " + getAttributeTypes().size() + ", index given: " + index + ")." );
			}

			attributeType.setAttributeOrder(index);
			getAttributeTypes().add(index, attributeType);
		}
		
		this.attributeTypes.add(attributeType);
	}

	@Override
	public void removeAttributeType(AT attributeType) {
		if (attributeType != null && this.attributeTypes != null) {
			this.attributeTypes.remove(attributeType);
		}
	}

	@Override
	public Integer getId() {
		return customizableInstanceTypeId;
	}

	@Override
	public void setId(Integer id) {
		customizableInstanceTypeId = id;
	}

	@Override
	public List<AT> getAttributeTypes() {
		return attributeTypes;
	}

	@Override
	public void setAttributeTypes(List<AT> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}
}
