package org.openmrs.module.openhmis.commons.api.entity.model;

import java.util.Set;

public interface ICustomizableInstance<TAttribute extends InstanceAttribute> {
	Set<TAttribute> getAttributes();

	void setAttributes(Set<TAttribute> attributes);

	void addAttribute(TAttribute attribute);

	void removeAttribute(TAttribute attribute);
}
