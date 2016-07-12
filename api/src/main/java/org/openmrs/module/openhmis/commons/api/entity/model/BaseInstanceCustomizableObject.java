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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.openmrs.customdatatype.CustomValueDescriptor;

// @formatter:off
/**
 * Base class for {@link org.openmrs.OpenmrsObject} models that can be customized based on an
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType}
 * @param <TInstanceType> The model instance type class.
 * @param <TAttribute> The model attribute class.
 */
public abstract class BaseInstanceCustomizableObject<
			TInstanceType extends IInstanceType<?>,
			TAttribute extends IInstanceAttribute<?, ?, ?>>
		extends BaseCustomizableObject<TAttribute>
		implements IInstanceCustomizable<TInstanceType, TAttribute> {
// @formatter:on
	public static final long serialVersionUID = 1L;

	private TInstanceType instanceType;

	@SuppressWarnings({ "unchecked" })
	static <TA extends IInstanceAttribute, I extends IInstanceCustomizable> void addAttribute(I instance, TA attribute) {
		if (attribute == null) {
			throw new NullPointerException("The attribute to add must be defined.");
		}

		if (instance.getAttributes() == null) {
			// Using LinkedHashSet because it is ordered by entry versus HashSet which is not.
			instance.setAttributes(new LinkedHashSet<TA>());
		}

		attribute.setOwner(instance);
		instance.getAttributes().add(attribute);
	}

	@SuppressWarnings({ "unchecked" })
	static <TA extends IInstanceAttribute<?, ?, ?>, I extends IInstanceCustomizable<?, ? extends TA>> void removeAttribute(
	        I instance, TA attribute) {
		if (instance.getAttributes() == null || attribute == null) {
			return;
		}
		attribute.setOwner(null);
		instance.getAttributes().remove(attribute);
	}

	// @formatter:off
	public static <TA extends IInstanceAttribute<?, ?, ?>, I extends IInstanceCustomizable<?, ? extends TA>>
			Set<TA>	getActiveAttributes(I instance) {
	// @formatter:on
		Set<TA> ret = new HashSet<TA>();
		if (instance.getAttributes() != null) {
			for (TA attr : instance.getAttributes()) {
				if (!attr.getAttributeType().isRetired()) {
					ret.add(attr);
				}
			}
		}
		return ret;
	}

	// @formatter:off
	public static <TA extends IInstanceAttribute<?, ?, ?>, I extends IInstanceCustomizable<?, ? extends TA>>
			Set<TA> getActiveAttributes(I instance, CustomValueDescriptor ofType) {
	// @formatter:on
		Set<TA> ret = new HashSet<TA>();
		if (instance.getAttributes() != null) {
			for (TA attr : instance.getAttributes()) {
				if (attr.getAttributeType().equals(ofType) && !attr.getAttributeType().isRetired()) {
					ret.add(attr);
				}
			}
		}
		return ret;
	}

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
