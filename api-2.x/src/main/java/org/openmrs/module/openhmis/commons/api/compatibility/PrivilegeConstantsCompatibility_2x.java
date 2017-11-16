package org.openmrs.module.openhmis.commons.api.compatibility;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.util.PrivilegeConstants;
import org.openmrs.web.ApplicationPrivilegeConstants;

/**
 * Specific implementation for the privileges to be pulled from the OpenMRS class using available properties
 */
@OpenmrsProfile(openmrsPlatformVersion = "2.*")
public class PrivilegeConstantsCompatibility_2x implements PrivilegeConstantsCompatibility {

	/* Commons privilege constants */
	public final String GET_LOCATIONS = PrivilegeConstants.GET_LOCATIONS;
	public final String GET_CONCEPT_CLASSES = PrivilegeConstants.GET_CONCEPT_CLASSES;
	public final String GET_USERS = PrivilegeConstants.GET_USERS;
	public final String GET_PROVIDERS = PrivilegeConstants.GET_PROVIDERS;
	public final String GET_CONCEPTS = PrivilegeConstants.GET_CONCEPTS;

	/* Cashier Module privilege constants */
	public final String GET_VISITS = PrivilegeConstants.GET_VISITS;
	public final String GET_ENCOUNTERS = PrivilegeConstants.GET_ENCOUNTERS;
	public final String GET_OBS = PrivilegeConstants.GET_OBS;
	public final String GET_PATIENTS = PrivilegeConstants.GET_PATIENTS;
	public final String DASHBOARD_SUMMARY = ApplicationPrivilegeConstants.DASHBOARD_SUMMARY;
	public final String DASHBOARD_DEMOGRAPHICS = ApplicationPrivilegeConstants.DASHBOARD_DEMOGRAPHICS;
	public final String DASHBOARD_OVERVIEW = ApplicationPrivilegeConstants.DASHBOARD_OVERVIEW;
	public final String DASHBOARD_VISITS = ApplicationPrivilegeConstants.DASHBOARD_VISITS;

	/* Inventory Module privilege constants */
	public final String GET_ROLES = PrivilegeConstants.GET_ROLES;
}
