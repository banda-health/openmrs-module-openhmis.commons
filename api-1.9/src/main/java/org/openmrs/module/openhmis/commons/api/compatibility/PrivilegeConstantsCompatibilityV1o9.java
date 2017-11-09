package org.openmrs.module.openhmis.commons.api.compatibility;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.util.PrivilegeConstants;

/**
 * Specific implementation for the privileges to be pulled from the OpenMRS class using available properties
 */
@OpenmrsProfile(openmrsPlatformVersion = "1.9.9 - 1.12.*")
public class PrivilegeConstantsCompatibilityV1o9
        implements PrivilegeConstantsCompatibility {

	/* Commons privilege constants */
	public final String GET_LOCATIONS = PrivilegeConstants.VIEW_LOCATIONS;
	public final String GET_CONCEPT_CLASSES = PrivilegeConstants.VIEW_CONCEPT_CLASSES;
	public final String GET_USERS = PrivilegeConstants.VIEW_USERS;
	public final String GET_PROVIDERS = PrivilegeConstants.VIEW_PROVIDERS;
	public final String GET_CONCEPTS = PrivilegeConstants.VIEW_CONCEPTS;

	/* Cashier Module privilege constants */
	public final String GET_VISITS = PrivilegeConstants.VIEW_VISITS;
	public final String GET_ENCOUNTERS = PrivilegeConstants.VIEW_ENCOUNTERS;
	public final String GET_OBS = PrivilegeConstants.VIEW_OBS;
	public final String GET_PATIENTS = PrivilegeConstants.VIEW_PATIENTS;
	public final String DASHBOARD_SUMMARY = PrivilegeConstants.DASHBOARD_SUMMARY;
	public final String DASHBOARD_DEMOGRAPHICS = PrivilegeConstants.DASHBOARD_DEMOGRAPHICS;
	public final String DASHBOARD_OVERVIEW = PrivilegeConstants.DASHBOARD_OVERVIEW;
	public final String DASHBOARD_VISITS = PrivilegeConstants.DASHBOARD_VISITS;

	/* Inventory Module privilege constants */
	public final String GET_ROLES = PrivilegeConstants.VIEW_ROLES;
}
