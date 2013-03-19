package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.OpenmrsData;

public abstract class BaseDataInstanceAttribute<TOwner extends OpenmrsData, TAttributeType extends InstanceAttributeType>
		extends BaseOpenmrsData
		implements InstanceAttribute<TOwner, TAttributeType> {

	private Integer attributeId;
	private TOwner owner;
	private TAttributeType attributeType;
	private String value;

	@Override
	public Integer getId() {
		return attributeId;
	}

	@Override
	public void setId(Integer id) {
		attributeId = id;
	}

	public TOwner getOwner() {
		return owner;
	}

	public void setOwner(TOwner owner) {
		this.owner = owner;
	}

	public TAttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(TAttributeType attributeType) {
		this.attributeType = attributeType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
