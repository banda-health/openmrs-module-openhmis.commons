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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.customdatatype.CustomValueDescriptor;

// @formatter:off
/**
 * Base class for {@link org.openmrs.OpenmrsObject} models that can be customized.
 * @param <TAttribute> The model attribute class.
 */
public abstract class BaseCustomizableObject<TAttribute extends IAttribute<?, ?>>
		extends BaseOpenmrsObject
		implements ICustomizable<TAttribute> {
// @formatter:on
	public static final long serialVersionUID = 0L;

	private Set<TAttribute> attributes;

	protected void onAddAttribute(TAttribute attribute) {
		// Just here to allow subclass to add custom logic
	}

	protected void onRemoveAttribute(TAttribute attribute) {
		// Just here to allow subclass to add custom logic
	}

	@Override
	public Set<TAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(Set<TAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Set<TAttribute> getActiveAttributes() {
		return getActiveAttributes(null);
	}

	@Override
	public Set<TAttribute> getActiveAttributes(CustomValueDescriptor ofType) {
		Set<TAttribute> result = new HashSet<TAttribute>();
		if (getAttributes() != null) {
			for (TAttribute attribute : getAttributes()) {
				if ((ofType == null || attribute.getAttributeType().equals(ofType))
				        && !attribute.getAttributeType().isRetired()) {
					result.add(attribute);
				}
			}
		}

		return result;
	}

	@Override
	public void addAttribute(TAttribute attribute) {
		if (attribute == null) {
			throw new NullPointerException("The attribute to add must be defined.");
		}

		Set<TAttribute> attributes = this.getAttributes();
		if (attributes == null) {
			// Using LinkedHashSet because it is ordered by entry versus HashSet which is not.
			attributes = new LinkedHashSet<TAttribute>();

			setAttributes(attributes);
		}

		onAddAttribute(attribute);
		attributes.add(attribute);
	}

	@Override
	public void removeAttribute(TAttribute attribute) {
		if (getAttributes() == null || attribute == null) {
			return;
		}

		onRemoveAttribute(attribute);
		getAttributes().remove(attribute);
	}
}
