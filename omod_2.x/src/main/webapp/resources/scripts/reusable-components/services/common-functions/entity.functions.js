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

(function() {
	'use strict';
	
	var app = angular.module('app.entityFunctionsFactory', []);
	app.service('EntityFunctions', EntityFunctions);
	
	EntityFunctions.$inject = [];
	
	function EntityFunctions() {
		var service = {
			retireUnretireDeletePopup: retireUnretireDeletePopup,
			addExtraFormatListElements: addExtraFormatListElements
		};

		return service;

		/**
		 * Show the retire/unretire and Delete popup
		 * @param selectorId - div id
		 */
		function retireUnretireDeletePopup(selectorId){
			var dialog = emr.setupConfirmationDialog({
				selector: '#' + selectorId,
				actions: {
					cancel: function(){
						dialog.close();
					}
				}
			});
			dialog.show();
		}
		
		function addExtraFormatListElements(formatFields) {
			for (var format in formatFields) {
				switch (formatFields[format]) {
					// As per PersonAttributeTypeFormController.java, remove inapplicable formats
					case "java.util.Date" :
					case "org.openmrs.Patient.exitReason" :
					case "org.openmrs.DrugOrder.discontinuedReason" :
						formatFields[format] = undefined;
						break;
				}
			}

			var undefinedId = _.indexOf(formatFields, undefined);
			while (undefinedId !== -1) {
				formatFields.splice(undefinedId, 1);
				undefinedId = _.indexOf(formatFields, undefined);
			}

			formatFields.unshift("java.lang.Character");
			formatFields.unshift("java.lang.Integer");
			formatFields.unshift("java.lang.Float");
			formatFields.unshift("java.lang.Boolean");
			return formatFields;
		}
	}
})();
