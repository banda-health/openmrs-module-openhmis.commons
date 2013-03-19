package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.BaseOpenmrsMetadata;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class BaseCustomizableInstanceMetadata<TAttribute extends InstanceAttribute>
	extends BaseOpenmrsMetadata {
	private Set<TAttribute> attributes;

	public Set<TAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<TAttribute> attributes) {
		this.attributes = attributes;
	}

	@SuppressWarnings("unchecked")
	public void addAttribute(TAttribute attribute) {
		if (attribute == null) {
			throw new NullPointerException("The attribute to add must be defined.");
		}

		if (attributes == null) {
			// Using LinkedHashSet because it is ordered by entry versus HashSet which is not.
			attributes = new LinkedHashSet<TAttribute>();
		}

		attribute.setOwner(this);
		attributes.add(attribute);
	}

	public void removeAttribute(TAttribute attribute) {
		if (attributes == null || attribute == null) {
			return;
		}

		attributes.remove(attribute);
	}
}

