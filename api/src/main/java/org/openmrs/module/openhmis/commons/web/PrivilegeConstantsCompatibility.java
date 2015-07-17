package org.openmrs.module.openhmis.commons.web;

import org.openmrs.Privilege;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;

/**
 * Gets the correct default view visit privilege needed.
 * @return A set containing the default set of privileges.
 */
public class PrivilegeConstantsCompatibility {
	
	public static final String GET_PROVIDERS = "Get Providers";
	public static final String VIEW_PROVIDERS = "View Providers";
	public static final String GET_VISITS = "Get Visits";
	public static final String VIEW_VISITS = "View Visits";
	
	public String getVisit() {
		String GET_VISIT_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_VISITS);
		
		if (privilege != null) {
			GET_VISIT_PRIVILEGE = GET_VISITS;
		} else {
			GET_VISIT_PRIVILEGE = VIEW_VISITS;
		}
		
		return GET_VISIT_PRIVILEGE;
	}
	
	public String getProviders() {
		String GET_PROVIDERS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_PROVIDERS);
		
		if (privilege != null) {
			GET_PROVIDERS_PRIVILEGE = GET_PROVIDERS;
		} else {
			GET_PROVIDERS_PRIVILEGE = VIEW_PROVIDERS;
		}
		
		return GET_PROVIDERS_PRIVILEGE;
	}
}
