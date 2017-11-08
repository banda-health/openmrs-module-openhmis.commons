package org.openmrs.module.openhmis.commons.api.compatibility;

/**
 * A layer to allow privilege constants to be selected and have their assignment of different OpenMRS versions done elsewhere
 */
public interface PrivilegeConstantsCompatibility {

	/* Commons privilege constants */
	String GET_LOCATIONS = new String();
	String GET_CONCEPT_CLASSES = new String();
	String GET_USERS = new String();
	String GET_PROVIDERS = new String();
	String GET_CONCEPTS = new String();

	/* Cashier Module privilege constants */
	String GET_VISITS = new String();
	String GET_ENCOUNTERS = new String();
	String GET_OBS = new String();
	String GET_PATIENTS = new String();

	/* Inventory Module privilege constants */
	String GET_ROLES = new String();
}
