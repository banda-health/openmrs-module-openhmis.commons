package org.openmrs.module.openhmis.commons.api.util;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.openhmis.commons.api.compatibility.PrivilegeConstants;

/**
 * Specific implementation for the privileges to be pulled from the OpenMRS class using available properties
 */
@OpenmrsProfile(openmrsPlatformVersion = "2.*")
public class PrivilegeConstantsV2o0 implements PrivilegeConstants {

	public final String GET_LOCATIONS = org.openmrs.util.PrivilegeConstants.GET_LOCATIONS;

	public final String GET_CONCEPT_CLASSES =
	        org.openmrs.util.PrivilegeConstants.GET_CONCEPT_CLASSES;

	public final String GET_USERS = org.openmrs.util.PrivilegeConstants.GET_USERS;

	public final String GET_PROVIDERS = org.openmrs.util.PrivilegeConstants.GET_PROVIDERS;

	public final String GET_CONCEPTS = org.openmrs.util.PrivilegeConstants.GET_CONCEPTS;
}
