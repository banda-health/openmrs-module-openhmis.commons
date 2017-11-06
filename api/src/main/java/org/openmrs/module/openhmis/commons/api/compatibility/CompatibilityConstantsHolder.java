package org.openmrs.module.openhmis.commons.api.compatibility;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * A class holder for the constants conditionally loaded that can be used in the OMOD
 */
public class CompatibilityConstantsHolder {

	@Autowired
	public static PrivilegeConstants privilegeConstants;
}
