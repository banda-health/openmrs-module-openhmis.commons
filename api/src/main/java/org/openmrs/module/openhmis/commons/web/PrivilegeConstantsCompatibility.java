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
	
	public String checkPrivilege(String para1, String para2) {
		String privilege1;
		Privilege privilege = Context.getUserService().getPrivilege(para1);
		
		if (privilege != null) {
			privilege1 = para1;
		} else {
			if (para2 == null) {
				throw new NullPointerException("The privilege you are referring to is missing");
			} else {
				privilege1 = para2;
			}
		}
		
		return privilege1;
	}
	
	public String getAddEncountersPrivilege() {
		String para1 = ADD_ENCOUNTERS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getAddVisitsPrivilege() {
		String para1 = ADD_VISITS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getEditEncountersPrivilege() {
		String para1 = EDIT_ENCOUNTERS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getEditPatientsPrivilege() {
		String para1 = EDIT_PATIENTS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getEditVisitsPrivilege() {
		String para1 = EDIT_VISITS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getDashboardSummaryPrivilege() {
		String para1 = DASHBOARD_SUMMARY;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getDashboardDemographicsPrivilege() {
		String para1 = DASHBOARD_DEMOGRAPHICS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getDashboardOverviewPrivilege() {
		String para1 = DASHBOARD_OVERVIEW;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getDashboardVisitsPrivilege() {
		String para1 = DASHBOARD_VISITS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewAdminFunctionsPrivilege() {
		String para1 = GET_ADMIN_FUNCTIONS;
		String para2 = VIEW_ADMIN_FUNCTIONS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewEncountersPrivilege() {
		String para1 = GET_ENCOUNTERS;
		String para2 = VIEW_ENCOUNTERS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewNavigationMenuPrivilege() {
		String para1 = GET_NAVIGATION_MENU;
		String para2 = VIEW_NAVIGATION_MENU;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewObsPrivilege() {
		String para1 = GET_OBS;
		String para2 = VIEW_OBS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewPatientsPrivilege() {
		String para1 = GET_PATIENTS;
		String para2 = VIEW_PATIENTS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewVisitPrivilege() {
		String para1 = GET_VISITS;
		String para2 = VIEW_VISITS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewProvidersPrivilege() {
		String para1 = GET_PROVIDERS;
		String para2 = VIEW_PROVIDERS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewRolesPrivilege() {
		String para1 = GET_ROLES;
		String para2 = VIEW_ROLES;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewUsersPrivilege() {
		String para1 = GET_USERS;
		String para2 = VIEW_USERS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewConceptsPrivilege() {
		String para1 = GET_CONCEPTS;
		String para2 = VIEW_CONCEPTS;
		return checkPrivilege(para1, para2);
	}
	
	public String getViewLocationsPrivilege() {
		String para1 = GET_LOCATIONS;
		String para2 = VIEW_LOCATIONS;
		return checkPrivilege(para1, para2);
	}
	
	public String getEditPatientIdentifiersPrivilege() {
		String para1 = EDIT_PATIENT_IDENTIFIERS;
		String para2 = null;
		return checkPrivilege(para1, para2);
	}
}
