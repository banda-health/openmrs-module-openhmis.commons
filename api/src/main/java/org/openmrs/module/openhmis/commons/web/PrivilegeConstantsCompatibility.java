package org.openmrs.module.openhmis.commons.web;

import org.openmrs.Privilege;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.exception.PrivilegeException;

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
	public static final String ADD_ENCOUNTERS = "Add Encounters";
	public static final String ADD_VISITS = "Add Visits";
	public static final String EDIT_ENCOUNTERS = "Edit Encounters";
	public static final String EDIT_PATIENTS = "Edit Patients";
	public static final String EDIT_VISITS = "Edit Visits";
	public static final String DASHBOARD_SUMMARY = "Patient Dashboard - View Patient Summary";
	public static final String DASHBOARD_DEMOGRAPHICS = "Patient Dashboard - View Demographics Section";
	public static final String DASHBOARD_OVERVIEW = "Patient Dashboard - View Overview Section";
	public static final String DASHBOARD_VISITS = "Patient Dashboard - View Visits Section";
	public static final String VIEW_ADMIN_FUNCTIONS = "View Administration Functions";
	public static final String GET_ADMIN_FUNCTIONS = "Get Administration Functions";
	public static final String VIEW_ENCOUNTERS = "View Encounters";
	public static final String GET_ENCOUNTERS = "Get Encounters";
	public static final String VIEW_NAVIGATION_MENU = "View Navigation Menu";
	public static final String GET_NAVIGATION_MENU = "Get Navigation Menu";
	public static final String VIEW_OBS = "View Observations";
	public static final String GET_OBS = "Get Observations";
	public static final String VIEW_PATIENTS = "View Patients";
	public static final String GET_PATIENTS = "Get Patients";
	
	/*Inventory module privilege constants*/
	public static final String GET_ROLES = "Get Roles";
	public static final String VIEW_ROLES = "View Roles";
	public static final String GET_USERS = "Get Users";
	public static final String VIEW_USERS = "View Users";
	public static final String GET_CONCEPTS = "Get Concepts";
	public static final String VIEW_CONCEPTS = "View Concepts";
	public static final String GET_LOCATIONS = "Get Locations";
	public static final String VIEW_LOCATIONS = "View Locations";
	public static final String EDIT_PATIENT_IDENTIFIERS = "Edit Patient Identifiers";
	
	private String checkPrivilege(String currentPrivilegeName, String oldPrivilegeName) {
		String privilegeName;
		Privilege privilege = Context.getUserService().getPrivilege(currentPrivilegeName);
		
		if (privilege != null) {
			privilegeName = currentPrivilegeName;
		} else {
			if (oldPrivilegeName == null) {
				throw new PrivilegeException("The privilege " + currentPrivilegeName + " was not found in the openmrs "
				        + "privilege list.");
			} else {
				privilegeName = oldPrivilegeName;
			}
		}
		
		return privilegeName;
	}
	
	public String getAddEncountersPrivilege() {
		String currentPrivilegeName = ADD_ENCOUNTERS;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getAddVisitsPrivilege() {
		String currentPrivilegeName = ADD_VISITS;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getEditEncountersPrivilege() {
		String currentPrivilegeName = EDIT_ENCOUNTERS;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getEditPatientsPrivilege() {
		String currentPrivilegeName = EDIT_PATIENTS;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getEditVisitsPrivilege() {
		String currentPrivilegeName = EDIT_VISITS;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getDashboardSummaryPrivilege() {
		String currentPrivilegeName = DASHBOARD_SUMMARY;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getDashboardDemographicsPrivilege() {
		String currentPrivilegeName = DASHBOARD_DEMOGRAPHICS;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getDashboardOverviewPrivilege() {
		String currentPrivilegeName = DASHBOARD_OVERVIEW;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getDashboardVisitsPrivilege() {
		String currentPrivilegeName = DASHBOARD_VISITS;
		return checkPrivilege(currentPrivilegeName, null);
	}
	
	public String getViewAdminFunctionsPrivilege() {
		String currentPrivilegeName = GET_ADMIN_FUNCTIONS;
		String oldPrivilegeName = VIEW_ADMIN_FUNCTIONS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewEncountersPrivilege() {
		String currentPrivilegeName = GET_ENCOUNTERS;
		String oldPrivilegeName = VIEW_ENCOUNTERS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewNavigationMenuPrivilege() {
		String currentPrivilegeName = GET_NAVIGATION_MENU;
		String oldPrivilegeName = VIEW_NAVIGATION_MENU;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewObsPrivilege() {
		String currentPrivilegeName = GET_OBS;
		String oldPrivilegeName = VIEW_OBS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewPatientsPrivilege() {
		String currentPrivilegeName = GET_PATIENTS;
		String oldPrivilegeName = VIEW_PATIENTS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewVisitPrivilege() {
		String currentPrivilegeName = GET_VISITS;
		String oldPrivilegeName = VIEW_VISITS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewProvidersPrivilege() {
		String currentPrivilegeName = GET_PROVIDERS;
		String oldPrivilegeName = VIEW_PROVIDERS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewRolesPrivilege() {
		String currentPrivilegeName = GET_ROLES;
		String oldPrivilegeName = VIEW_ROLES;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewUsersPrivilege() {
		String currentPrivilegeName = GET_USERS;
		String oldPrivilegeName = VIEW_USERS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewConceptsPrivilege() {
		String currentPrivilegeName = GET_CONCEPTS;
		String oldPrivilegeName = VIEW_CONCEPTS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getViewLocationsPrivilege() {
		String currentPrivilegeName = GET_LOCATIONS;
		String oldPrivilegeName = VIEW_LOCATIONS;
		return checkPrivilege(currentPrivilegeName, oldPrivilegeName);
	}
	
	public String getEditPatientIdentifiersPrivilege() {
		String currentPrivilegeName = EDIT_PATIENT_IDENTIFIERS;
		return checkPrivilege(currentPrivilegeName, null);
	}
}
