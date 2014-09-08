package org.openmrs.module.openhmis.commons.api.util;

import org.apache.commons.lang.StringUtils;
import org.openmrs.User;

/**
 * Helper class for working with {@link org.openmrs.Privilege}s.
 */
public class PrivilegeUtil {
	protected PrivilegeUtil() {}
	
	/**
	 * Checks if the specified user has all of the comma separated privileges.
	 * @param user The user to check
	 * @param privileges The privilege or comma separated list of privileges
	 * @return {@code true} if the user has all the privileges; otherwise, {@code false}.
	 */
	public static boolean hasPrivileges(User user, String privileges) {
		if (StringUtils.isEmpty(privileges)) {
			return true;
		}
		
		String[] privs = StringUtils.split(privileges, ',');
		
		return hasPrivileges(user, privs);
	}
	
	/**
	 * Checks if the specified user has all of the specified privileges.
	 * @param user The user to check
	 * @param privileges The privileges
	 * @return {@code true} if the user has all the privileges; otherwise, {@code false}.
	 */
	public static boolean hasPrivileges(User user, String... privileges) {
		if (user == null) {
			throw new IllegalArgumentException("The user to check must be defined.");
		}
		
		if (privileges == null || privileges.length == 0) {
			return true;
		}
		
		boolean result = true;
		for (String priv : privileges) {
			String trimmed = priv.trim();
			if (!user.hasPrivilege(trimmed)) {
				result = false;
				break;
			}
		}
		
		return result;
	}
}
