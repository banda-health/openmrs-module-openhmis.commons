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
package org.openmrs.module.openhmis.commons.attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Attributable;
import org.openmrs.customdatatype.NotYetPersistedException;
import org.openmrs.module.openhmis.commons.api.Utility;
import org.openmrs.util.OpenmrsClassLoader;

/**
 * Utility class for working with {@link org.openmrs.attribute.Attribute}'s.
 */
public class AttributeUtil {
	private static final Log LOG = LogFactory.getLog(AttributeUtil.class);

	private AttributeUtil() {}

	/**
	 * Attempts to create a new instance of the specified class and hydrate (deserialize) it using the specified string
	 * value.
	 * @param className The class name for the expected instance
	 * @param value The serialized object data
	 * @return A new hydrated instance or {@code null} if the instance could not be loaded.
	 */
	@SuppressWarnings("unchecked")
	public static Object tryToHydrateObject(String className, String value) {
		// TODO: Refactor this.
		//  This method assumes a lot about what kind of class is being used to store the serialized data
		//  (Attributable). If we assume that the data is in an Attributable than this method can be simplified.  If
		//  not, it should use the general java serialization stuff unless the class is some type we know about and can
		//  do some kind of special deserialization for.

		Object result = value;

		try {
			Class cls = Class.forName(className);
			if (Attributable.class.isAssignableFrom(cls)) {
				try {
					Class c = OpenmrsClassLoader.getInstance().loadClass(className);

					// Attempt to hydrate the attribute using Attributable.hydrate(String)
					try {
						Object instance = c.newInstance();

						Attributable attr = Utility.as(Attributable.class, instance);
						if (attr != null) {
							result = attr.hydrate(value);
						}
					} catch (InstantiationException e) {
						// try to hydrate the object with the String constructor
						LOG.trace("Unable to call no-arg constructor for class: " + c.getName());

						result = c.getConstructor(String.class).newInstance(value);
					}
				} catch (NotYetPersistedException e) {
					result = null;
				} catch (Exception ex) {
					LOG.warn("Unable to hydrate value: " + value + " for type: " + className, ex);
				}
			}
		} catch (ClassNotFoundException cnfe) {
			LOG.warn("Unable to parse '" + className + "' to a known class.");
		}

		return result;
	}
}
