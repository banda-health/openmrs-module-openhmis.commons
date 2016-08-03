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
var requirejs = {
	baseUrl: COMMONS_RESOURCES_URL,
	catchError: true,
	paths: {
		'reusable-components': REUSABLE_COMPONENTS_URL
	}
};

// handle general exceptions
var ohmis = {};
ohmis.handleException = function() {
	return function(exception, cause) {
		// unknown provider..
		var exc = String(exception);
		if (exc.indexOf("unpr") !== -1) {
			console.log(exc);
		} else if (exc.indexOf("session") !== -1 || exc.indexOf("timeout") !== -1) {
			console.log(exc + " - " + cause);
			emr.message("SESSION TIMEOUT");
		} else {
			console.log(exc + " - " + cause);
			emr.message(cause);
		}
	};
}
