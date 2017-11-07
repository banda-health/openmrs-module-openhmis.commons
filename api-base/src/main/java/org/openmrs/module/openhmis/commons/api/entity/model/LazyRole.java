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

import org.openmrs.Role;

/**
 * An {@link org.openmrs.Role} which can be configured to be lazy-loaded from the database.
 */
public class LazyRole extends Role {
	public static final long serialVersionUID = 0L;

	public LazyRole() {
		super();
	}

	public LazyRole(Role role) {
		setName(role.getName());
		setChangedBy(role.getChangedBy());
		setChildRoles(role.getChildRoles());
		setCreator(role.getCreator());
		setDateChanged(role.getDateChanged());
		setDateCreated(role.getDateCreated());
		setDateRetired(role.getDateRetired());
		setDescription(role.getDescription());
		setInheritedRoles(role.getInheritedRoles());
		setPrivileges(role.getPrivileges());
		setRetired(role.getRetired());
		setRetiredBy(role.getRetiredBy());
		setRetireReason(role.getRetireReason());
		setRole(role.getRole());
		setUuid(role.getUuid());
	}
}
