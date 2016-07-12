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

/**
 * Base class for instance attribute type models.
 * @param <TOwner> The owning instance type.
 */
public class BaseInstanceAttributeType<TOwner extends IInstanceType<?>> extends BaseAttributeType
        implements IInstanceAttributeType<TOwner> {
	public static final long serialVersionUID = 1L;

	private TOwner owner;

	/**
	 * Gets the attribute type owner.
	 * @return The owner
	 */
	public TOwner getOwner() {
		return owner;
	}

	/**
	 * Sets the attribute type owner.
	 * @param parent The owner
	 */
	public void setOwner(TOwner parent) {
		this.owner = parent;
	}
}
