/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 *
 */

package org.openmrs.module.openhmis.commons.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.module.openhmis.commons.model.RoleCreationViewModel;
import org.openmrs.util.RoleConstants;
import org.openmrs.web.WebConstants;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base Controller to manage the Role Creation pages.
 */
public abstract class RoleCreationControllerBase {
	private static final Log LOG = LogFactory.getLog(RoleCreationControllerBase.class);

	public abstract UserService getUserService();

	public abstract Set<Privilege> privileges();

	@RequestMapping(method = RequestMethod.GET)
	public void render(ModelMap model, HttpServletRequest request) throws IOException {
		List<Role> roles = getUserService().getAllRoles();
		model.addAttribute("roles", roles);
		HeaderController.render(model, request);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void submit(HttpServletRequest request, RoleCreationViewModel viewModel, Errors errors, ModelMap model)
	        throws IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");

		if (action.equals("add")) {
			addPrivileges(viewModel.getAddToRole());
			session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "openhmis.commons.roleCreation.page.feedback.add");
		} else if (action.equals("remove")) {
			removePrivileges(viewModel.getRemoveFromRole());
			session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "openhmis.commons.roleCreation.page.feedback.remove");
		} else if (action.equals("new") && newRoleValidated(viewModel, errors)) {
			createRole(viewModel, session);
		}

		render(model, request);
	}

	private void addPrivileges(String roleUuid) {
		Role role = getUserService().getRoleByUuid(roleUuid);
		if (role == null) {
			throw new APIException("The role '" + roleUuid + "' could not be found.");
		}

		for (Privilege priv : privileges()) {
			if (!role.hasPrivilege(priv.getName())) {
				role.addPrivilege(priv);
			}
		}

		getUserService().saveRole(role);
	}

	private void removePrivileges(String roleUuid) {
		Role role = getUserService().getRoleByUuid(roleUuid);
		if (role == null) {
			throw new APIException("The role '" + roleUuid + "' could not be found.");
		}

		for (Privilege priv : privileges()) {
			if (role.hasPrivilege(priv.getName())) {
				role.removePrivilege(priv);
			}
		}

		getUserService().saveRole(role);
	}

	private void createRole(RoleCreationViewModel viewModel, HttpSession session) {
		Role newRole = new Role();
		newRole.setRole(viewModel.getNewRoleName());
		newRole.setDescription("Creates users with the module defined privileges");
		newRole.setPrivileges(privileges());

		Role inheritedRole = getUserService().getRole(RoleConstants.PROVIDER);
		Set<Role> inheritedRoles = new HashSet<Role>();
		inheritedRoles.add(inheritedRole);
		newRole.setInheritedRoles(inheritedRoles);

		getUserService().saveRole(newRole);

		session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "openhmis.commons.roleCreation.page.feedback.new");
	}

	private boolean newRoleValidated(RoleCreationViewModel viewModel, Errors errors) {
		if (viewModel.getNewRoleName().equals(StringUtils.EMPTY)) {
			errors.rejectValue("role", "openhmis.commons.roleCreation.page.feedback.error.blankRole");
			return false;
		} else if (checkForDuplicateRole(viewModel.getNewRoleName())) {
			errors.rejectValue("role", "openhmis.commons.roleCreation.page.feedback.error.existingRole");
			return false;
		}

		return true;
	}

	private Boolean checkForDuplicateRole(String role) {
		for (Role name : getUserService().getAllRoles()) {
			if (name.getRole().equals(role)) {
				return true;
			}
		}

		return false;
	}
}
