package org.openmrs.module.openhmis.commons.api;

/**
 * Represents types that must be initialized before being used.
 */
public interface Initializable {
	/**
	 * Performs the type initialization.
	 */
	void initialize();
}

