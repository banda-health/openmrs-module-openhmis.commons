package org.openmrs.module.openhmis.commons.api.entity.model;

import java.util.List;
import java.util.Vector;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;

public abstract class BaseCustomizableInstanceType<TInstanceType extends BaseCustomizableInstanceType<TInstanceType, AT>, AT extends InstanceAttributeType<TInstanceType>>
		extends BaseOpenmrsMetadata
		implements InstanceType<TInstanceType, AT> {
	private Integer customizableInstanceTypeId;
	private List<AT> attributeTypes;
	
	public AT addAttributeType(String name, String format, Integer foreignKey, String regExp, boolean required, Integer attributeOrder) {
		AT attributeType = getAttributeTypeInstance();

		attributeType.setOwner((TInstanceType) this);

		attributeType.setName(name);
		attributeType.setFormat(format);
		attributeType.setForeignKey(foreignKey);
		attributeType.setRequired(required);
		
		addAttributeType(null, attributeType);

		return attributeType;
	}

	public void addAttributeType(AT attributeType) {
		addAttributeType(null, attributeType);
	}

	public void addAttributeType(Integer index, AT attributeType) {
		if (attributeType == null) {
			throw new NullPointerException("The payment mode attribute type to add must be defined.");
		}

		if (attributeType.getOwner() != this) {
			attributeType.setOwner((TInstanceType) this);
		}

		if (this.attributeTypes == null) {
			this.attributeTypes = new Vector<AT>();
		}
		
		if (index == null) {
			attributeType.setAttributeOrder(getAttributeTypes().size());
			getAttributeTypes().add(attributeType);
		}
		else {
			if (index > getAttributeTypes().size())
				throw new APIException("Invalid attribute order. Should not leave space in the list (list length: " + getAttributeTypes().size() + ", index given: " + index + ")." );
			getAttributeTypes().add(index, attributeType);
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

	public List<AT> getAttributeTypes() {
		return attributeTypes;
	}

	public void setAttributeTypes(List<AT> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}
}
