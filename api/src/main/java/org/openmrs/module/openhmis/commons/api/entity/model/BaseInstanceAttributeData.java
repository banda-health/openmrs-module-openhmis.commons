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

import org.openmrs.module.openhmis.commons.attribute.AttributeUtil;

/**
 * Base class for {@link org.openmrs.OpenmrsData} instance attribute models.
 * @param <TOwner> The class of the owning model.
 * @param <TAttributeType> The class of the attribute type.
 */
public abstract class BaseInstanceAttributeData<TOwner extends ICustomizableInstance<?, ?>, //
TAttributeType extends IInstanceAttributeType<?>> //
        extends BaseSerializableOpenmrsData implements IInstanceAttribute<TOwner, TAttributeType> {
	public static final long serialVersionUID = 0L;
	
	private Integer attributeId;
	private TOwner owner;
	private TAttributeType attributeType;
	private String value;
	
	@Override
	public Integer getId() {
		return attributeId;
	}
	
	@Override
	public void setId(Integer id) {
		attributeId = id;
	}
	
	public TOwner getOwner() {
		return owner;
	}
	
	public void setOwner(TOwner owner) {
		this.owner = owner;
	}
	
	public TAttributeType getAttributeType() {
		return attributeType;
	}
	
	public void setAttributeType(TAttributeType attributeType) {
		this.attributeType = attributeType;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Object getHydratedValue() {
		return AttributeUtil.tryToHydrateObject(getAttributeType().getFormat(), value);
	}
}
