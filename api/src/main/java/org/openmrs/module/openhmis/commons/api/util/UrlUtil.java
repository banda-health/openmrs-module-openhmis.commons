package org.openmrs.module.openhmis.commons.api.util;

/**
 * Utility class for URLs
 */
public class UrlUtil {

	private UrlUtil() {}

	/**
	 * Adds the '.form' ending to the specified page, if it does not already exist.
	 * @param page The page to add the form ending to.
	 * @return The page with '.form' appended to the end.
	 */
	public static String formUrl(String page) {
		return page.endsWith(".form") ? page : page + ".form";
	}

	/**
	 * Creates the redirect url for the specified page.
	 * @param page The page to redirect to.
	 * @return The redirect url.
	 */
	public static String redirectUrl(String page) {
		return "redirect:" + formUrl(page);
	}
}
