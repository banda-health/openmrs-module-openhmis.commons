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
import org.openmrs.util.OpenmrsClassLoader;

public class AttributeUtil {
	private static final Log log = LogFactory.getLog(AttributeUtil.class);

	public static Object tryToHydrateObject(String className, String key) {
		try {
			Class c = OpenmrsClassLoader.getInstance().loadClass(className);
			try {
				Object o = c.newInstance();
				if (o instanceof Attributable) {
					Attributable attr = (Attributable) o;
					return attr.hydrate(key);
				}
			}
			catch (InstantiationException e) {
				// try to hydrate the object with the String constructor
				log.trace("Unable to call no-arg constructor for class: " + c.getName());
				Object o = c.getConstructor(String.class).newInstance(key);
				return o;
			}
		}
		catch (NotYetPersistedException e) {
			return null;
		}
		catch (Throwable t) {
			log.warn("Unable to hydrate value: " + key + " for type: " + className, t);
		}
		
		log.debug("Returning value: '" + key + "'");
		return key;
	}
}
