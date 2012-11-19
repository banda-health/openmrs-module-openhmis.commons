package org.openmrs.module.openhmis.commons.api;

/**
 * Utility methods type.
 */
public class Utility {
	// Do not allow types to be instantiated.
	private Utility() { }

	public static <T> T as(Class<T> cls, Object o){
		if(cls.isInstance(o)){
			return cls.cast(o);
		}
		return null;
	}
}

