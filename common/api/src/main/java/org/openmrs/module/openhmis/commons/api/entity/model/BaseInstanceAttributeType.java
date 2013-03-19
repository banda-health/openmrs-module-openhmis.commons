package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.OpenmrsObject;

public abstract class BaseInstanceAttributeType<TParent extends OpenmrsObject>
		extends BaseOpenmrsMetadata
		implements InstanceAttributeType<TParent> {
	private Integer attributeTypeId;
	private TParent parent;
	private Integer attributeOrder;

	private String format;
	private Integer foreignKey;

	private String regExp;
	private Boolean required = false;

	@Override
	public Integer getId() {
		return attributeTypeId;
	}

	@Override
	public void setId(Integer id) {
		attributeTypeId = id;
	}

	public TParent getOwner() {
		return parent;
	}

	public void setOwner(TParent parent) {
		this.parent = parent;
	}

	public Integer getAttributeOrder() {
		return attributeOrder;
	}

	public void setAttributeOrder(Integer attributeOrder) {
		this.attributeOrder = attributeOrder;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(Integer foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getRegExp() {
		return regExp;
	}

	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
}

