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
