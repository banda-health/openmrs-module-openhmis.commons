package org.openmrs.module.openhmis.commons.api.compatibility;

/**
 * A layer to allow privilege constants to be selected and have their assignment of different OpenMRS versions done elsewhere
 */
public abstract class PrivilegeConstantsCompatibility {

	/* Commons privilege constants */
	public static String GET_LOCATIONS = new String();
	public static String GET_CONCEPT_CLASSES = new String();
	public static String GET_USERS = new String();
	public static String GET_PROVIDERS = new String();
	public static String GET_CONCEPTS = new String();

	/* Cashier Module privilege constants */
	public static String GET_VISITS = new String();
	public static String GET_ENCOUNTERS = new String();
	public static String GET_OBS = new String();
	public static String GET_PATIENTS = new String();
	public static String DASHBOARD_SUMMARY = new String();
	public static String DASHBOARD_DEMOGRAPHICS = new String();
	public static String DASHBOARD_OVERVIEW = new String();
	public static String DASHBOARD_VISITS = new String();

	/* Inventory Module privilege constants */
	public static String GET_ROLES = new String();
}
