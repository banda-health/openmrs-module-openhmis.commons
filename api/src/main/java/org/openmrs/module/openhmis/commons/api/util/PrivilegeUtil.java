package org.openmrs.module.openhmis.commons.api.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.exception.PrivilegeException;
import org.openmrs.module.openhmis.commons.api.f.Func1;

/**
 * Helper class for working with {@link org.openmrs.Privilege}s.
 */
public class PrivilegeUtil {

	private static final Log LOG = LogFactory.getLog(PrivilegeUtil.class);

	private PrivilegeUtil() {}

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
	public static boolean hasPrivileges(final User user, String... privileges) {
		if (user == null) {
			throw new IllegalArgumentException("The user to check must be defined.");
		}

		if (privileges == null || privileges.length == 0) {
			return true;
		}

		Func1<String, Boolean> hasPrivFunc;
		User currentUser = Context.getAuthenticatedUser();
		if (user == currentUser) {
			hasPrivFunc = new Func1<String, Boolean>() {
				@Override
				public Boolean apply(String priv) {
					return Context.hasPrivilege(priv);
				}
			};
		} else {
			hasPrivFunc = new Func1<String, Boolean>() {
				@Override
				public Boolean apply(String priv) {
					return user.hasPrivilege(priv);
				}
			};
		}

		boolean result = true;
		for (String priv : privileges) {
			String trimmed = priv.trim();
			if (!hasPrivFunc.apply(trimmed)) {
				result = false;
				break;
			}
		}

		return result;
	}

	public static void requirePrivileges(User user, String privileges) {
		boolean hasPrivileges = hasPrivileges(user, privileges);
		if (!hasPrivileges) {
			LOG.error("Privileges are missing. The required privilege is <" + privileges + ">");
			throw new PrivilegeException();
		}
	}

}
