package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.customdatatype.CustomValueDescriptor;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public abstract class BaseCustomizableInstanceObject<TAttribute extends InstanceAttribute>
	extends BaseOpenmrsObject
	implements ICustomizableInstance<TAttribute> {
	private Set<TAttribute> attributes;

	@Override
	public Set<TAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public Set<TAttribute> getActiveAttributes() {
		return getActiveAttributes(this);
	}
	
	@Override
	public Set<TAttribute> getActiveAttributes(CustomValueDescriptor ofType) {
		return getActiveAttributes(this, ofType);
	}

	@Override
	public void setAttributes(Set<TAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void addAttribute(TAttribute attribute) {
		addAttribute(this, attribute);
	}

	@Override
	public void removeAttribute(TAttribute attribute) {
		removeAttribute(this, attribute);
	}
	
	@SuppressWarnings({ "unchecked" })
	public static <TA extends InstanceAttribute, I extends ICustomizableInstance<TA>>
		void addAttribute(I instance, TA attribute) {
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
	public static <TA extends InstanceAttribute, I extends ICustomizableInstance<TA>>
			void removeAttribute(I instance, TA attribute) {
		if (instance.getAttributes() == null || attribute == null) {
			return;
		}
		attribute.setOwner(null);
		instance.getAttributes().remove(attribute);
	}
	
	public static <TA extends InstanceAttribute, I extends ICustomizableInstance<TA>>
			Set<TA> getActiveAttributes(I instance) {
		Set<TA> ret = new HashSet<TA>();
		if (instance.getAttributes() != null)
			for (TA attr : instance.getAttributes())
				if (!attr.getAttributeType().isRetired())
					ret.add(attr);
		return ret;
	}
	
	public static <TA extends InstanceAttribute, I extends ICustomizableInstance<TA>>
			Set<TA> getActiveAttributes(I instance, CustomValueDescriptor ofType) {
		Set<TA> ret = new HashSet<TA>();
		if (instance.getAttributes() != null)
			for (TA attr : instance.getAttributes())
				if (attr.getAttributeType().equals(ofType) && !attr.getAttributeType().isRetired())
					ret.add(attr);
		return ret;
	}
}
