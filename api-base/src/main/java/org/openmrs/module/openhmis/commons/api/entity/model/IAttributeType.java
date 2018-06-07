/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.OpenmrsMetadata;
import org.openmrs.customdatatype.CustomValueDescriptor;

/**
 * Represents classes that define attribute type information.
 */
public interface IAttributeType extends OpenmrsMetadata, CustomValueDescriptor {
	Integer getAttributeOrder();

	void setAttributeOrder(Integer attributeOrder);

	String getFormat();

	void setFormat(String format);

	Integer getForeignKey();

	void setForeignKey(Integer foreignKey);

	String getRegExp();

	void setRegExp(String regExp);

	Boolean getRequired();

	void setRequired(Boolean required);
}
