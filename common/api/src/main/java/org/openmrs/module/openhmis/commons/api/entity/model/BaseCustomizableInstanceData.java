package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.customdatatype.CustomValueDescriptor;

import java.util.Set;

public abstract class BaseCustomizableInstanceData<TAttribute extends InstanceAttribute>
		extends BaseOpenmrsData
		implements ICustomizableInstance<TAttribute> {
	private Set<TAttribute> attributes;

	public Set<TAttribute> getAttributes() {
		return attributes;
	}
	
	@Override
	public Set<TAttribute> getActiveAttributes() {
		return BaseCustomizableInstanceObject.getActiveAttributes(this);
	}
	
	@Override
	public Set<TAttribute> getActiveAttributes(CustomValueDescriptor ofType) {
		return BaseCustomizableInstanceObject.getActiveAttributes(this, ofType);
	}

	@Override
	public void setAttributes(Set<TAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void addAttribute(TAttribute attribute) {
		BaseCustomizableInstanceObject.addAttribute(this, attribute);
	}

	@Override
	public void removeAttribute(TAttribute attribute) {
		BaseCustomizableInstanceObject.removeAttribute(this, attribute);
	}
}

