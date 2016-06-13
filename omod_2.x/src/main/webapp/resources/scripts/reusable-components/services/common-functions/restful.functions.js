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

	angular.module('app.restfulServices').service('CommonsRestfulFunctions', CommonsRestfulFunctions);

	CommonsRestfulFunctions.$inject = ['EntityRestFactory'];

	function CommonsRestfulFunctions(EntityRestFactory) {
		var service;

		service = {
			searchPatients: searchPatients,
			searchStockOperationItems: searchStockOperationItems,
			getSessionLocation: getSessionLocation,
			endVisit: endVisit,
			loadVisit: loadVisit,
		};

		return service;

		/**
		 * Patient search
		 * @param module_name
		 * @param q
		 * @param currentPage
		 * @param limit
		 * @param onLoadPatientsSuccessful
		 */
		function searchPatients(module_name, q, currentPage, limit, onLoadPatientsSuccessful) {
			var requestParams = []; // PaginationService.paginateParams(currentPage, limit, false, '');
			requestParams['q'] = q;
			requestParams['rest_entity_name'] = '';
			requestParams['v'] = "custom:(uuid,patientIdentifier:(uuid,identifier)," +
				"person:(gender,age,birthdate,birthdateEstimated,personName))";
			EntityRestFactory.setBaseUrl('patient', 'v1');
			EntityRestFactory.loadEntities(requestParams,
				onLoadPatientsSuccessful, errorCallback);
			//reset base url..
			EntityRestFactory.setBaseUrl(module_name);
		}

		function searchStockOperationItems(module_name, q) {
			var requestParams = {};
			requestParams['has_physical_inventory'] = 'true';
			requestParams['q'] = q;
			requestParams['limit'] = 10;
			requestParams['startIndex'] = 1;

			return EntityRestFactory.autocompleteSearch(requestParams, 'item', module_name);
		}

		function getSessionLocation(module_name, onLoadSessionLocationSuccessful) {
			var requestParams = [];
			requestParams['rest_entity_name'] = 'session';
			EntityRestFactory.setBaseUrl('appui', 'v1');
			EntityRestFactory.loadEntities(requestParams,
				onLoadSessionLocationSuccessful, errorCallback);
			//reset base url..
			EntityRestFactory.setBaseUrl(module_name);
		}

		function endVisit(module_name, visit_uuid, stopDatetime, onLoadEndVisitSuccessful) {
			var requestParams = {};
			requestParams['stopDatetime'] = stopDatetime.toString();
			EntityRestFactory.setBaseUrl('', 'v1');
			EntityRestFactory.post('visit', visit_uuid, requestParams,
				onLoadEndVisitSuccessful,
				errorCallback
			);
			//reset base url..
			EntityRestFactory.setBaseUrl(module_name);
		}

		function loadVisit(module_name, patient_uuid, onLoadVisitSuccessful) {
			var requestParams = [];
			requestParams['rest_entity_name'] = '';
			requestParams['patient'] = patient_uuid;
			EntityRestFactory.setBaseUrl('visit', 'v1');
			EntityRestFactory.loadEntities(requestParams,
				onLoadVisitSuccessful, errorCallback);
			//reset base url..
			EntityRestFactory.setBaseUrl(module_name);
		}

		function errorCallback(error) {
			console.log(error);
		}

	}
})();
