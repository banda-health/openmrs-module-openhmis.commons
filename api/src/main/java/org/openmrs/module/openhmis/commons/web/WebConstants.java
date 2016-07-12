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

	public static final String REPORT_DOWNLOAD_URL = "/moduleServlet/jasperreport/jreportDownload";

	protected WebConstants() {}
}
