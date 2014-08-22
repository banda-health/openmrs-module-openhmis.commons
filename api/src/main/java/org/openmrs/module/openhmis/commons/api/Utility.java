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
package org.openmrs.module.openhmis.commons.api;

/**
 * General utility methods.
 */
public class Utility {
	// Do not allow this class to be instantiated.
	private Utility() {}
	
	/**
	 * Returns the specified object as the specified class or returns null if the cast is not
	 * supported.
	 * @param cls The generic class to cast the object to.
	 * @param o The object to cast.
	 * @param <T> The generic class to cast the object to.
	 * @return The object cast to the specified class or {@code null} if the cast is not supported.
	 */
	public static <T> T as(Class<T> cls, Object o) {
		if (cls.isInstance(o)) {
			return cls.cast(o);
		}
		return null;
	}
}
