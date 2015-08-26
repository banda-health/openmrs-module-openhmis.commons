package org.openmrs.module.openhmis.commons.model;

/**
 * A view model used by role creation pages.
 */
public class RoleCreationViewModel {
	private String addToRole;
	private String removeFromRole;
	private String newRoleName;
	private String role;

	public String getAddToRole() {
		return addToRole;
	}

	public void setAddToRole(String addToRole) {
		this.addToRole = addToRole;
	}

	public String getRemoveFromRole() {
		return removeFromRole;
	}

	public void setRemoveFromRole(String removeFromRole) {
		this.removeFromRole = removeFromRole;
	}

	public String getNewRoleName() {
		return newRoleName;
	}

	public void setNewRoleName(String newRoleName) {
		this.newRoleName = newRoleName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
