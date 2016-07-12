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
package org.openmrs.module.openhmis.commons.api.exception;

import org.openmrs.api.APIException;

/**
 * Exception thrown when there are problems with privileges
 */
public class PrivilegeException extends APIException {

	public static final long serialVersionUID = 22323L;

	public PrivilegeException() {
		super();
	}

	public PrivilegeException(String message) {
		super(message);
	}

	public PrivilegeException(Throwable cause) {
		super(cause);
	}

	public PrivilegeException(String message, Throwable cause) {
		super(message, cause);
	}

}
