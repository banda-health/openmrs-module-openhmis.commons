package org.openmrs.module.openhmis.commons.api.compatibility;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.util.PrivilegeConstants;
import org.openmrs.web.ApplicationPrivilegeConstants;

/**
 * Specific implementation for the privileges to be pulled from the OpenMRS class using available properties
 */
@OpenmrsProfile(openmrsPlatformVersion = "2.*")
public class PrivilegeConstantsCompatibility_2x extends PrivilegeConstantsCompatibility {

	static {
		/* Commons privilege constants */
		GET_LOCATIONS = PrivilegeConstants.GET_LOCATIONS;
		GET_CONCEPT_CLASSES = PrivilegeConstants.GET_CONCEPT_CLASSES;
		GET_USERS = PrivilegeConstants.GET_USERS;
		GET_PROVIDERS = PrivilegeConstants.GET_PROVIDERS;
		GET_CONCEPTS = PrivilegeConstants.GET_CONCEPTS;

		/* Cashier Module privilege constants */
		GET_VISITS = PrivilegeConstants.GET_VISITS;
		GET_ENCOUNTERS = PrivilegeConstants.GET_ENCOUNTERS;
		GET_OBS = PrivilegeConstants.GET_OBS;
		GET_PATIENTS = PrivilegeConstants.GET_PATIENTS;
		DASHBOARD_SUMMARY = ApplicationPrivilegeConstants.DASHBOARD_SUMMARY;
		DASHBOARD_DEMOGRAPHICS = ApplicationPrivilegeConstants.DASHBOARD_DEMOGRAPHICS;
		DASHBOARD_OVERVIEW = ApplicationPrivilegeConstants.DASHBOARD_OVERVIEW;
		DASHBOARD_VISITS = ApplicationPrivilegeConstants.DASHBOARD_VISITS;

		/* Inventory Module privilege constants */
		GET_ROLES = PrivilegeConstants.GET_ROLES;
	}
}
