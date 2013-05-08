package org.openmrs.module.openhmis.commons.attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Attributable;
import org.openmrs.attribute.AttributeType;
import org.openmrs.attribute.BaseAttribute;
import org.openmrs.customdatatype.Customizable;
import org.openmrs.customdatatype.NotYetPersistedException;
import org.openmrs.util.OpenmrsClassLoader;

@SuppressWarnings("rawtypes")
public abstract class HydrateableAttribute<AT extends AttributeType, OwningType extends Customizable<?>>
		extends BaseAttribute<AT, OwningType> {
	
	private static final Log log = LogFactory.getLog(HydrateableAttribute.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getValue() {
		try {
			Class c = OpenmrsClassLoader.getInstance().loadClass(getAttributeType().getDatatypeClassname());
			try {
				Object o = c.newInstance();
				if (o instanceof Attributable) {
					Attributable attr = (Attributable) o;
					return attr.hydrate(getValueReference());
				}
			}
			catch (InstantiationException e) {
				// try to hydrate the object with the String constructor
				log.trace("Unable to call no-arg constructor for class: " + c.getName());
				Object o = c.getConstructor(String.class).newInstance(getValueReference());
				return o;
			}
		}
		catch (NotYetPersistedException e) {
			return null;
		}
		catch (Throwable t) {
			log.warn("Unable to hydrate value: " + getValueReference() + " for type: " + getAttributeType(), t);
		}
		
		log.debug("Returning value: '" + getValueReference() + "'");
		return getValueReference();
	}
}
