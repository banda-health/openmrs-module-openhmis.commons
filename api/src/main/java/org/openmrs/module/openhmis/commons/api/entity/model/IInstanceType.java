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

import java.util.List;

import org.openmrs.OpenmrsMetadata;

/**
 * Represents a class that defines the type of an {@link IInstanceCustomizable}. Each {@link IInstanceType} has attributes
 * that are only used by instances of that type and not shared with other instance types.
 * @param <TAttributeType> The instance attribute type class.
 */
public interface IInstanceType<TAttributeType extends IInstanceAttributeType<?>> extends OpenmrsMetadata {
	/**
	 * Gets the {@link TAttributeType}'s for this {@link IInstanceType}.
	 * @return The attribute types.
	 */
	List<TAttributeType> getAttributeTypes();

	/**
	 * Sets the {@link TAttributeType}'s for this {@link IInstanceType}.
	 * @param attributeTypes The attribute types.
	 */
	void setAttributeTypes(List<TAttributeType> attributeTypes);

	/**
	 * Adds the specified {@link TAttributeType}.
	 * @param attributeType The attribute type to add.
	 */
	void addAttributeType(TAttributeType attributeType);

	/**
	 * Adds the specified {@link TAttributeType} at the specified index.
	 * @param index The index where the attribute type will be inserted or {@code null} to insert at the end.
	 * @param attributeType The attribute type to add.
	 */
	void addAttributeType(Integer index, TAttributeType attributeType);

	/**
	 * Removes the specified {@link TAttributeType}.
	 * @param attributeType The attribute type to remove.
	 */
	void removeAttributeType(TAttributeType attributeType);
}
