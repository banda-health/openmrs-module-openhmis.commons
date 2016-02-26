(function() {
	'use strict';
	
	var app = angular.module('app.entityFunctionsFactory', []);
	app.service('EntityFunctions', EntityFunctions);
	
	EntityFunctions.$inject = [];
	
	function EntityFunctions() {
		var service;
		service = {
			addExtraFormatListElements : addExtraFormatListElements
		};
		
		return service;
		
		function addExtraFormatListElements(formatFields) {
			for ( var format in formatFields) {
				switch (formatFields[format]) {
					// As per PersonAttributeTypeFormController.java, remove inapplicable formats
					case "java.util.Date" :
					case "org.openmrs.Patient.exitReason" :
					case "org.openmrs.DrugOrder.discontinuedReason" :
						formatFields[format] = undefined;
						break;
				}
			}

			do {
				var undefinedId = _.indexOf(formatFields, undefined);

				if (undefinedId !== -1)
					formatFields.splice(undefinedId, 1);
			} while (undefinedId !== -1)

			formatFields.unshift("java.lang.Character");
			formatFields.unshift("java.lang.Integer");
			formatFields.unshift("java.lang.Float");
			formatFields.unshift("java.lang.Boolean");
			return formatFields;
		}
	}
	
})();
