package org.openhmis.common;

/**
 * Represents types that must be initialized before being used.
 */
public interface Initializable {
	/**
	 * Performs the type initialization.
	 */
	void initialize();
}

