package org.openmrs.module.openhmis.commons.api.compatibility;

import org.openmrs.annotation.OpenmrsProfile;

/**
 * Specific implementation for the privileges to be pulled from the OpenMRS class using available properties
 */
@OpenmrsProfile(openmrsPlatformVersion = "1.9.9 - 1.12.*")
public class PrivilegeConstantsV1o9 implements PrivilegeConstants {

	public final String GET_LOCATIONS = org.openmrs.util.PrivilegeConstants.VIEW_LOCATIONS;

	public final String GET_CONCEPT_CLASSES =
	        org.openmrs.util.PrivilegeConstants.VIEW_CONCEPT_CLASSES;

	public final String GET_USERS = org.openmrs.util.PrivilegeConstants.VIEW_USERS;

	public final String GET_PROVIDERS = org.openmrs.util.PrivilegeConstants.VIEW_PROVIDERS;

	public final String GET_CONCEPTS = org.openmrs.util.PrivilegeConstants.VIEW_CONCEPTS;
}
