package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.OpenmrsObject;

public interface InstanceAttribute<TOwner extends ICustomizableInstance, TAttributeType extends InstanceAttributeType>
	extends OpenmrsObject {

	TOwner getOwner();
	void setOwner(TOwner owner);

	TAttributeType getAttributeType();
	void setAttributeType(TAttributeType attributeType);

	String getValue();
	void setValue(String value);
}
