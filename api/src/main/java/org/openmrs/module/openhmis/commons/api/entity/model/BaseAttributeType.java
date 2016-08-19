/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.BaseOpenmrsMetadata;

/**
 * Base class for attribute type models.
 */
public abstract class BaseAttributeType extends BaseOpenmrsMetadata implements IAttributeType {
	private Integer attributeTypeId;
	private Integer attributeOrder;
	private String format;
	private Integer foreignKey;
	private String regExp;
	private Boolean required = false;

	/**
	 * Gets the attribute type id.
	 * @return The attribute type id
	 */
	@Override
	public Integer getId() {
		return this.attributeTypeId;
	}

	/**
	 * Sets the attribute type id.
	 * @param id The attribute type id
	 */
	@Override
	public void setId(Integer id) {
		this.attributeTypeId = id;
	}

	/**
	 * Gets the attribute order.
	 * @return The attribute order
	 */
	public Integer getAttributeOrder() {
		return attributeOrder;
	}

	/**
	 * Sets the attribute order.
	 * @param attributeOrder The attribute order
	 */
	public void setAttributeOrder(Integer attributeOrder) {
		this.attributeOrder = attributeOrder;
	}

	/**
	 * Gets the attribute type format.
	 * @return The format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the attribute type format.
	 * @param format The format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Gets the attribute type foreign key.
	 * @return The foreign key
	 */
	public Integer getForeignKey() {
		return foreignKey;
	}

	/**
	 * Sets the attribute type foreign key.
	 * @param foreignKey THe foreign key
	 */
	public void setForeignKey(Integer foreignKey) {
		this.foreignKey = foreignKey;
	}

	/**
	 * Gets the attribute type regular expression.
	 * @return The regular expression
	 */
	public String getRegExp() {
		return regExp;
	}

	/**
	 * Sets the attribute type regular expression
	 * @param regExp The regular expression
	 */
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	/**
	 * Gets whether this attribute type is required.
	 * @return {@code true} if the attribute type is required; otherwise, {@code false}
	 */
	public Boolean getRequired() {
		return required;
	}

	/**
	 * Sets whether this attribute type is required.
	 * @param required {@code true} if the attribute type is required; otherwise, {@code false}
	 */
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
