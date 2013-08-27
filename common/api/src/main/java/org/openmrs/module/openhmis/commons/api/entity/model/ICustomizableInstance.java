/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
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
