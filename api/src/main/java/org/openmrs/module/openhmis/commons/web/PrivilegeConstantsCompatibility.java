package org.openmrs.module.openhmis.commons.web;

import org.openmrs.Privilege;
import org.openmrs.api.context.Context;

/**
 * Gets the correct default view visit privilege needed.
 * @return A set containing the default set of privileges.
 */
public class PrivilegeConstantsCompatibility {
	/*Cashier module privilege constants*/
	public static final String GET_PROVIDERS = "Get Providers";
	public static final String VIEW_PROVIDERS = "View Providers";
	public static final String GET_VISITS = "Get Visits";
	public static final String VIEW_VISITS = "View Visits";
	
	/*Inventory module privilege constants*/
	public static final String GET_ROLES = "Get Roles";
	public static final String VIEW_ROLES = "View Roles";
	public static final String GET_USERS = "Get Users";
	public static final String VIEW_USERS = "View Users";
	public static final String GET_CONCEPTS = "Get Concepts";
	public static final String VIEW_CONCEPTS = "View Concepts";
	public static final String GET_LOCATIONS = "Get Locations";
	public static final String VIEW_LOCATIONS = "View Locations";
	
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
	
	public String getRoles() {
		String GET_ROLES_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_ROLES);
		
		if (privilege != null) {
			GET_ROLES_PRIVILEGE = GET_ROLES;
		} else {
			GET_ROLES_PRIVILEGE = VIEW_ROLES;
		}
		
		return GET_ROLES_PRIVILEGE;
	}
	
	public String getUsers() {
		String GET_USERS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_USERS);
		
		if (privilege != null) {
			GET_USERS_PRIVILEGE = GET_USERS;
		} else {
			GET_USERS_PRIVILEGE = VIEW_USERS;
		}
		
		return GET_USERS_PRIVILEGE;
	}
	
	public String getConcepts() {
		String GET_CONCEPTS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_CONCEPTS);
		
		if (privilege != null) {
			GET_CONCEPTS_PRIVILEGE = GET_CONCEPTS;
		} else {
			GET_CONCEPTS_PRIVILEGE = VIEW_CONCEPTS;
		}
		
		return GET_CONCEPTS_PRIVILEGE;
	}
	
	public String getLocations() {
		String GET_LOCATIONS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_LOCATIONS);
		
		if (privilege != null) {
			GET_LOCATIONS_PRIVILEGE = GET_LOCATIONS;
		} else {
			GET_LOCATIONS_PRIVILEGE = VIEW_LOCATIONS;
		}
		
		return GET_LOCATIONS_PRIVILEGE;
	}
}
