/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.OpenmrsObject;

/**
 * Represents classes that define instance attribute data.
 * @param <TOwner> The parent {@link ICustomizableInstance} class.
 * @param <TAttributeType> The {@link IInstanceAttributeType} class.
 */
public interface IInstanceAttribute<TOwner extends ICustomizableInstance<?, ?>, //
TAttributeType extends IInstanceAttributeType<?>> //
        extends OpenmrsObject {
	TOwner getOwner();
	
	void setOwner(TOwner owner);
	
	TAttributeType getAttributeType();
	
	void setAttributeType(TAttributeType attributeType);
	
	String getValue();
	
	void setValue(String value);
	
	Object getHydratedValue();
}
