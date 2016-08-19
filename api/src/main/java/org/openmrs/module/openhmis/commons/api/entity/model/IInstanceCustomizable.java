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

import java.util.Set;

import org.openmrs.customdatatype.CustomValueDescriptor;

// @formatter:off
/**
 * Represents a class that can be customized with attributes based on a specific {@link IInstanceType} that defines what
 * {@link IInstanceAttributeType} are allowed.
 * @param <TInstanceType> The {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType} class.
 * @param <TAttribute> The {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttribute} class.
 */
public interface IInstanceCustomizable<
			TInstanceType extends IInstanceType<?>,
			TAttribute extends IInstanceAttribute<?, ?, ?>>
		extends ICustomizable<TAttribute> {
// @formatter:on
	/**
	 * Gets the {@link TAttribute}'s added to this instance.
	 * @return The attributes for this instance.
	 */
	Set<TAttribute> getAttributes();

	/**
	 * ISimpleCustomizable Sets the {@link TAttribute}'s for this instance.
	 * @param attributes The attributes for this instance.
	 */
	void setAttributes(Set<TAttribute> attributes);

	/**
	 * Adds an {@link TAttribute} to the attributes for this instance.
	 * @param attribute The attribute to add.
	 */
	void addAttribute(TAttribute attribute);

	/**
	 * Removes an {@link TAttribute} from the attributes for this instance.
	 * @param attribute The attribute to remove.
	 */
	void removeAttribute(TAttribute attribute);

	/**
	 * Gets the active (that is, not retired) {@link TAttribute}'s for this instance.
	 * @return The active attributes.
	 */
	Set<TAttribute> getActiveAttributes();

	/**
	 * Gets the active (that is, not retired) {@link TAttribute}'s of the specified type for this instance.
	 * @param ofType The attribute type.
	 * @return The active attributes.
	 */
	Set<TAttribute> getActiveAttributes(CustomValueDescriptor ofType);

	/**
	 * Gets the {@link TInstanceType} for this instance.
	 * @return The instance type.
	 */
	TInstanceType getInstanceType();

	/**
	 * Sets the {@link TInstanceType} for this instance.
	 * @param type The instance type.
	 */
	void setInstanceType(TInstanceType type);
}
