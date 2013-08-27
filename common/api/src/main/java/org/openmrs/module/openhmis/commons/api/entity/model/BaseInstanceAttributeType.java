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

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.OpenmrsObject;

public  class BaseInstanceAttributeType<TOwner extends OpenmrsObject>
		extends BaseOpenmrsMetadata
		implements InstanceAttributeType<TOwner> {
	private Integer attributeTypeId;
	private TOwner owner;
	private Integer attributeOrder;

	private String format;
	private Integer foreignKey;

	private String regExp;
	private Boolean required = false;

	@Override
	public Integer getId() {
		return attributeTypeId;
	}

	@Override
	public void setId(Integer id) {
		attributeTypeId = id;
	}

	public TOwner getOwner() {
		return owner;
	}

	public void setOwner(TOwner parent) {
		this.owner = parent;
	}

	public Integer getAttributeOrder() {
		return attributeOrder;
	}

	public void setAttributeOrder(Integer attributeOrder) {
		this.attributeOrder = attributeOrder;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(Integer foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getRegExp() {
		return regExp;
	}

	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@Override
	public String getDatatypeClassname() {
		return getFormat();
	}

	@Override
	public String getDatatypeConfig() {
		return getForeignKey().toString();
	}

	@Override
	public String getPreferredHandlerClassname() {
		// Default to null to simplify concrete classes
		return null;
	}

	@Override
	public String getHandlerConfig() {
		// Default to null to simplify concrete classes		
		return null;
	}
}

