/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
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
		return checkPrivilege(ADD_ENCOUNTERS, null);
	}

	public String getAddVisitsPrivilege() {
		return checkPrivilege(ADD_VISITS, null);
	}

	public String getEditEncountersPrivilege() {
		return checkPrivilege(EDIT_ENCOUNTERS, null);
	}

	public String getEditPatientsPrivilege() {
		return checkPrivilege(EDIT_PATIENTS, null);
	}

	public String getEditVisitsPrivilege() {
		return checkPrivilege(EDIT_VISITS, null);
	}

	public String getDashboardSummaryPrivilege() {
		return checkPrivilege(DASHBOARD_SUMMARY, null);
	}

	public String getDashboardDemographicsPrivilege() {
		return checkPrivilege(DASHBOARD_DEMOGRAPHICS, null);
	}

	public String getDashboardOverviewPrivilege() {
		return checkPrivilege(DASHBOARD_OVERVIEW, null);
	}

	public String getDashboardVisitsPrivilege() {
		return checkPrivilege(DASHBOARD_VISITS, null);
	}

	public String getViewAdminFunctionsPrivilege() {
		return checkPrivilege(GET_ADMIN_FUNCTIONS, VIEW_ADMIN_FUNCTIONS);
	}

	public String getViewEncountersPrivilege() {
		return checkPrivilege(GET_ENCOUNTERS, VIEW_ENCOUNTERS);
	}

	public String getViewNavigationMenuPrivilege() {
		return checkPrivilege(GET_NAVIGATION_MENU, VIEW_NAVIGATION_MENU);
	}

	public String getViewObsPrivilege() {
		return checkPrivilege(GET_OBS, VIEW_OBS);
	}

	public String getViewPatientsPrivilege() {
		return checkPrivilege(GET_PATIENTS, VIEW_PATIENTS);
	}

	public String getViewVisitPrivilege() {
		return checkPrivilege(GET_VISITS, VIEW_VISITS);
	}

	public String getViewProvidersPrivilege() {
		return checkPrivilege(GET_PROVIDERS, VIEW_PROVIDERS);
	}

	public String getViewRolesPrivilege() {
		return checkPrivilege(GET_ROLES, VIEW_ROLES);
	}

	public String getViewUsersPrivilege() {
		return checkPrivilege(GET_USERS, VIEW_USERS);
	}

	public String getViewConceptsPrivilege() {
		return checkPrivilege(GET_CONCEPTS, VIEW_CONCEPTS);
	}

	public String getViewLocationsPrivilege() {
		return checkPrivilege(GET_LOCATIONS, VIEW_LOCATIONS);
	}

	public String getEditPatientIdentifiersPrivilege() {
		return checkPrivilege(EDIT_PATIENT_IDENTIFIERS, null);
	}
}
