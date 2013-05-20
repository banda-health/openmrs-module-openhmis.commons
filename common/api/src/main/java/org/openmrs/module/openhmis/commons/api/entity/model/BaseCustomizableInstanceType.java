package org.openmrs.module.openhmis.commons.api.entity.model;

import java.util.List;
import java.util.Vector;

import org.openmrs.BaseOpenmrsMetadata;

public abstract class BaseCustomizableInstanceType<TInstanceType extends BaseCustomizableInstanceType<TInstanceType>>
		extends BaseOpenmrsMetadata
		implements InstanceType<TInstanceType> {
	private Integer customizableInstanceTypeId;
	private List<InstanceAttributeType<TInstanceType>> attributeTypes;
	
	public InstanceAttributeType<TInstanceType> addAttributeType(String name, String format, Integer foreignKey, String regExp, boolean required) {
		InstanceAttributeType<TInstanceType> attributeType = getAttributeTypeInstance();

		attributeType.setOwner((TInstanceType) this);

		attributeType.setName(name);
		attributeType.setFormat(format);
		attributeType.setForeignKey(foreignKey);
		attributeType.setRequired(required);

		addAttributeType(attributeType);

		return attributeType;
	}

	public void addAttributeType(InstanceAttributeType<TInstanceType> attributeType) {
		if (attributeType == null) {
			throw new NullPointerException("The payment mode attribute type to add must be defined.");
		}

		if (attributeType.getOwner() != this) {
			attributeType.setOwner((TInstanceType) this);
		}

		if (this.attributeTypes == null) {
			this.attributeTypes = new Vector<InstanceAttributeType<TInstanceType>>();
		}

		this.attributeTypes.add(attributeType);
	}

	public void removeAttributeType(InstanceAttributeType<TInstanceType> attributeType) {
		if (attributeType != null && this.attributeTypes != null) {
			this.attributeTypes.remove(attributeType);
		}
	}

	// Getters & setters
	@Override
	public Integer getId() {
		return customizableInstanceTypeId;
	}

	@Override
	public void setId(Integer id) {
		customizableInstanceTypeId = id;
	}

	public List<InstanceAttributeType<TInstanceType>> getAttributeTypes() {
		return attributeTypes;
	}

	public void setAttributeTypes(List<InstanceAttributeType<TInstanceType>> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}
}
