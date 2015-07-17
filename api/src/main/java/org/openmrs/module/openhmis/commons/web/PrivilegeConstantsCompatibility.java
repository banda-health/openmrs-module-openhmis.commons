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
	
	public String getAddEncountersPrivilege() {
		String ADD_ENCOUNTERS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(ADD_ENCOUNTERS);
		
		if (privilege != null) {
			ADD_ENCOUNTERS_PRIVILEGE = ADD_ENCOUNTERS;
		}
		
		return ADD_ENCOUNTERS_PRIVILEGE;
	}
	
	public String getAddVisitsPrivilege() {
		String ADD_VISITS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(ADD_VISITS);
		
		if (privilege != null) {
			ADD_VISITS_PRIVILEGE = ADD_VISITS;
		}
		
		return ADD_VISITS_PRIVILEGE;
	}
	
	public String getEditEncountersPrivilege() {
		String EDIT_ENCOUNTERS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(EDIT_ENCOUNTERS);
		
		if (privilege != null) {
			EDIT_ENCOUNTERS_PRIVILEGE = EDIT_ENCOUNTERS;
		}
		
		return EDIT_ENCOUNTERS_PRIVILEGE;
	}
	
	public String getEditPatientsPrivilege() {
		String EDIT_PATIENTS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(EDIT_PATIENTS);
		
		if (privilege != null) {
			EDIT_PATIENTS_PRIVILEGE = EDIT_PATIENTS;
		}
		
		return EDIT_PATIENTS_PRIVILEGE;
	}
	
	public String getEditVisitsPrivilege() {
		String EDIT_VISITS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(EDIT_VISITS);
		
		if (privilege != null) {
			EDIT_VISITS_PRIVILEGE = EDIT_VISITS;
		}
		
		return EDIT_VISITS_PRIVILEGE;
	}
	
	public String getDashboardSummaryPrivilege() {
		String GET_DASHBOARD_SUMMARY_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(DASHBOARD_SUMMARY);
		
		if (privilege != null) {
			GET_DASHBOARD_SUMMARY_PRIVILEGE = DASHBOARD_SUMMARY;
		}
		
		return GET_DASHBOARD_SUMMARY_PRIVILEGE;
	}
	
	public String getDashboardDemographicsPrivilege() {
		String GET_DASHBOARD_DEMOGRAPHICS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(DASHBOARD_DEMOGRAPHICS);
		
		if (privilege != null) {
			GET_DASHBOARD_DEMOGRAPHICS_PRIVILEGE = DASHBOARD_DEMOGRAPHICS;
		}
		
		return GET_DASHBOARD_DEMOGRAPHICS_PRIVILEGE;
	}
	
	public String getDashboardOverviewPrivilege() {
		String GET_DASHBOARD_OVERVIEW_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(DASHBOARD_OVERVIEW);
		
		if (privilege != null) {
			GET_DASHBOARD_OVERVIEW_PRIVILEGE = DASHBOARD_OVERVIEW;
		}
		
		return GET_DASHBOARD_OVERVIEW_PRIVILEGE;
	}
	
	public String getDashboardVisitsPrivilege() {
		String GET_DASHBOARD_VISITS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(DASHBOARD_VISITS);
		
		if (privilege != null) {
			GET_DASHBOARD_VISITS_PRIVILEGE = DASHBOARD_VISITS;
		}
		
		return GET_DASHBOARD_VISITS_PRIVILEGE;
	}
	
	public String getViewAdminFunctionsPrivilege() {
		String GET_VIEW_ADMIN_FUNCTION_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_ADMIN_FUNCTIONS);
		
		if (privilege != null) {
			GET_VIEW_ADMIN_FUNCTION_PRIVILEGE = GET_ADMIN_FUNCTIONS;
		} else {
			GET_VIEW_ADMIN_FUNCTION_PRIVILEGE = VIEW_ADMIN_FUNCTIONS;
		}
		
		return GET_VIEW_ADMIN_FUNCTION_PRIVILEGE;
	}
	
	public String getViewEncountersPrivilege() {
		String GET_VIEW_ENCOUNTERS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_ENCOUNTERS);
		
		if (privilege != null) {
			GET_VIEW_ENCOUNTERS_PRIVILEGE = GET_ENCOUNTERS;
		} else {
			GET_VIEW_ENCOUNTERS_PRIVILEGE = VIEW_ENCOUNTERS;
		}
		
		return GET_VIEW_ENCOUNTERS_PRIVILEGE;
	}
	
	public String getViewNavigationMenuPrivilege() {
		String GET_VIEW_NAVIGATION_MENU_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_NAVIGATION_MENU);
		
		if (privilege != null) {
			GET_VIEW_NAVIGATION_MENU_PRIVILEGE = GET_NAVIGATION_MENU;
		} else {
			GET_VIEW_NAVIGATION_MENU_PRIVILEGE = VIEW_NAVIGATION_MENU;
		}
		
		return GET_VIEW_NAVIGATION_MENU_PRIVILEGE;
	}
	
	public String getViewObsPrivilege() {
		String GET_VIEW_OBS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_OBS);
		
		if (privilege != null) {
			GET_VIEW_OBS_PRIVILEGE = GET_OBS;
		} else {
			GET_VIEW_OBS_PRIVILEGE = VIEW_OBS;
		}
		
		return GET_VIEW_OBS_PRIVILEGE;
	}
	
	public String getViewPatientsPrivilege() {
		String GET_VIEW_PATIENTS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_PATIENTS);
		
		if (privilege != null) {
			GET_VIEW_PATIENTS_PRIVILEGE = GET_PATIENTS;
		} else {
			GET_VIEW_PATIENTS_PRIVILEGE = VIEW_PATIENTS;
		}
		
		return GET_VIEW_PATIENTS_PRIVILEGE;
	}
	
	public String getViewVisitPrivilege() {
		String GET_VISIT_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_VISITS);
		
		if (privilege != null) {
			GET_VISIT_PRIVILEGE = GET_VISITS;
		} else {
			GET_VISIT_PRIVILEGE = VIEW_VISITS;
		}
		
		return GET_VISIT_PRIVILEGE;
	}
	
	public String getViewProvidersPrivilege() {
		String GET_PROVIDERS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_PROVIDERS);
		
		if (privilege != null) {
			GET_PROVIDERS_PRIVILEGE = GET_PROVIDERS;
		} else {
			GET_PROVIDERS_PRIVILEGE = VIEW_PROVIDERS;
		}
		
		return GET_PROVIDERS_PRIVILEGE;
	}
	
	public String getViewRolesPrivilege() {
		String GET_ROLES_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_ROLES);
		
		if (privilege != null) {
			GET_ROLES_PRIVILEGE = GET_ROLES;
		} else {
			GET_ROLES_PRIVILEGE = VIEW_ROLES;
		}
		
		return GET_ROLES_PRIVILEGE;
	}
	
	public String getViewUsersPrivilege() {
		String GET_USERS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_USERS);
		
		if (privilege != null) {
			GET_USERS_PRIVILEGE = GET_USERS;
		} else {
			GET_USERS_PRIVILEGE = VIEW_USERS;
		}
		
		return GET_USERS_PRIVILEGE;
	}
	
	public String getViewConceptsPrivilege() {
		String GET_CONCEPTS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_CONCEPTS);
		
		if (privilege != null) {
			GET_CONCEPTS_PRIVILEGE = GET_CONCEPTS;
		} else {
			GET_CONCEPTS_PRIVILEGE = VIEW_CONCEPTS;
		}
		
		return GET_CONCEPTS_PRIVILEGE;
	}
	
	public String getViewLocationsPrivilege() {
		String GET_LOCATIONS_PRIVILEGE;
		Privilege privilege = Context.getUserService().getPrivilege(GET_LOCATIONS);
		
		if (privilege != null) {
			GET_LOCATIONS_PRIVILEGE = GET_LOCATIONS;
		} else {
			GET_LOCATIONS_PRIVILEGE = VIEW_LOCATIONS;
		}
		
		return GET_LOCATIONS_PRIVILEGE;
	}
	
	public String getEditPatientIdentifiersPrivilege() {
		String GET_EDIT_PARTIENT_IDENTIFIERS_PRIVILEGE = null;
		Privilege privilege = Context.getUserService().getPrivilege(EDIT_PATIENT_IDENTIFIERS);
		
		if (privilege != null) {
			GET_EDIT_PARTIENT_IDENTIFIERS_PRIVILEGE = EDIT_PATIENT_IDENTIFIERS;
		}
		
		return GET_EDIT_PARTIENT_IDENTIFIERS_PRIVILEGE;
	}
}
