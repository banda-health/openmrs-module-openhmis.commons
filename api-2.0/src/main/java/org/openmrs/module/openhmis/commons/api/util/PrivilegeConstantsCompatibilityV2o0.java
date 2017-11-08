package org.openmrs.module.openhmis.commons.api.util;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.openhmis.commons.api.compatibility.PrivilegeConstantsCompatibility;
import org.openmrs.util.PrivilegeConstants;

/**
 * Specific implementation for the privileges to be pulled from the OpenMRS class using available properties
 */
@OpenmrsProfile(openmrsPlatformVersion = "2.*")
public class PrivilegeConstantsCompatibilityV2o0 implements PrivilegeConstantsCompatibility {

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

	/* Inventory Module privilege constants */
	public final String GET_ROLES = PrivilegeConstants.GET_ROLES;
}
