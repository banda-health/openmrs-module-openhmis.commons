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

/**
 * Base constants used to store module web resources and urls.
 */
public class WebConstants {
	protected static final String MODULE_BASE = "/module/openhmis/";
	protected static final String MODULE_RESOURCE_BASE = "/moduleResources/openhmis/";
	
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
