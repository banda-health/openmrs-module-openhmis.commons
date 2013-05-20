package org.openmrs.module.openhmis.commons.api.entity.model;

import java.util.Set;

import org.openmrs.customdatatype.CustomValueDescriptor;

public interface ICustomizableInstance<TAttribute extends InstanceAttribute> {
	Set<TAttribute> getAttributes();
	
	Set<TAttribute> getActiveAttributes();
	Set<TAttribute> getActiveAttributes(CustomValueDescriptor ofType);

	void setAttributes(Set<TAttribute> attributes);

	void addAttribute(TAttribute attribute);

	void removeAttribute(TAttribute attribute);
}
