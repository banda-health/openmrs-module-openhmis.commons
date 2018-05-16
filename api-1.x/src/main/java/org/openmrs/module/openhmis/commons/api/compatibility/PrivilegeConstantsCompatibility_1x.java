package org.openmrs.module.openhmis.commons.api.compatibility;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.util.PrivilegeConstants;

/**
 * Specific implementation for the privileges to be pulled from the OpenMRS class using available properties
 */
@OpenmrsProfile(openmrsPlatformVersion = "1.9.9 - 1.12.*")
public class PrivilegeConstantsCompatibility_1x extends PrivilegeConstantsCompatibility {

	static {
		/* Commons privilege constants */
		GET_LOCATIONS = PrivilegeConstants.VIEW_LOCATIONS;
		GET_CONCEPT_CLASSES = PrivilegeConstants.VIEW_CONCEPT_CLASSES;
		GET_USERS = PrivilegeConstants.VIEW_USERS;
		GET_PROVIDERS = PrivilegeConstants.VIEW_PROVIDERS;
		GET_CONCEPTS = PrivilegeConstants.VIEW_CONCEPTS;

		/* Cashier Module privilege constants */
		GET_VISITS = PrivilegeConstants.VIEW_VISITS;
		GET_ENCOUNTERS = PrivilegeConstants.VIEW_ENCOUNTERS;
		GET_OBS = PrivilegeConstants.VIEW_OBS;
		GET_PATIENTS = PrivilegeConstants.VIEW_PATIENTS;
		DASHBOARD_SUMMARY = PrivilegeConstants.DASHBOARD_SUMMARY;
		DASHBOARD_DEMOGRAPHICS = PrivilegeConstants.DASHBOARD_DEMOGRAPHICS;
		DASHBOARD_OVERVIEW = PrivilegeConstants.DASHBOARD_OVERVIEW;
		DASHBOARD_VISITS = PrivilegeConstants.DASHBOARD_VISITS;

		/* Inventory Module privilege constants */
		GET_ROLES = PrivilegeConstants.VIEW_ROLES;
	}
}
