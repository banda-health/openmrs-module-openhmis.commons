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

	CommonsRestfulFunctions.$inject = ['EntityRestFactory', '$filter'];

	var ROOT_URL;
	var FIELD_ATTRIBUTES_FRAGMENT_URL = 'module/openhmis/common/fieldAttributes.page';

	function CommonsRestfulFunctions(EntityRestFactory, $filter) {
		var service;

		service = {
			searchPatients: searchPatients,
			getSessionLocation: getSessionLocation,
			endVisit: endVisit,
			loadVisit: loadVisit,
			populateFieldAttributesData: populateFieldAttributesData,
			searchPerson: searchPerson,
			loadStockRooms: loadStockRooms,
			searchItems: searchItems
		};

		return service;

		/**
		 * Patient search
		 * @param module_name,
		 * @param $scope
		 * @param q
		 * @param currentPage
		 * @param limit
		 */
		function searchPatients(module_name, q, currentPage, limit, $scope) {
			var requestParams = []; // PaginationService.paginateParams(currentPage, limit, false, '');
			requestParams['q'] = q;
			requestParams['rest_entity_name'] = '';
			requestParams['v'] = "custom:(uuid,patientIdentifier:(uuid,identifier)," +
				"person:(gender,age,birthdate,birthdateEstimated,personName))";
			EntityRestFactory.setBaseUrl('patient', 'v1');
			EntityRestFactory.loadEntities(requestParams,
				function(data) {
					$scope.patients = data.results;
					$scope.totalNumOfResults = $scope.patients.length;
					if (currentPage > 1) {
						var index = (currentPage - 1) * limit;
						$scope.patients.splice(0, index);
					}
					if ($scope.selectExistingPatient && $scope.patients.length > 0) {
						$scope.selectedPatient = $scope.patients[0];
						$scope.selectPatient($scope.selectedPatient);
					}
				}, errorCallback);

			EntityRestFactory.setBaseUrl(module_name);
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

		function endVisit(module_name, visit_uuid, $scope) {
			var requestParams = {};
			var stopDatetime = $filter('date')(new Date(), 'yyyy-MM-ddThh:mm:ss.sss');
			requestParams['stopDatetime'] = stopDatetime.toString();
			EntityRestFactory.setBaseUrl('', 'v1');
			EntityRestFactory.post('visit', visit_uuid, requestParams,
				function(data) {
					if (data.stopDatetime !== undefined) {
						$scope.visit = undefined;
					}
				},
				errorCallback
			);

			EntityRestFactory.setBaseUrl(module_name);
		}

		function loadVisit(module_name, patient_uuid, $scope) {
			var requestParams = [];
			requestParams['rest_entity_name'] = '';
			requestParams['patient'] = patient_uuid;
			EntityRestFactory.setBaseUrl('visit', 'v1');
			EntityRestFactory.loadEntities(requestParams,
				function(data) {
					if (data.results) {
						$scope.visit = data.results[0];
					} else {
						$scope.visit = '';
					}
				}, errorCallback);

			EntityRestFactory.setBaseUrl(module_name);
		}

		function populateFieldAttributesData(base_url, fieldAttributesData, attributeTypes) {
			ROOT_URL = base_url;
			for (var i = 0; i < attributeTypes.length; i++) {
				var attribute = attributeTypes[i];
				if (attribute.format === 'org.openmrs.Concept' && attribute.foreignKey !== null) {
					getFieldAttributesData('concept', attribute.foreignKey, function(data) {
						if (data !== undefined) {
							fieldAttributesData[data.foreignKey] = data.results;
						}
					});
				} else if (attribute.format === 'org.openmrs.User') {
					getFieldAttributesData('user', '', function(data) {
						if (data !== undefined) {
							fieldAttributesData['users'] = data.results;
						}
					});
				} else if (attribute.format === 'org.openmrs.Location') {
					getFieldAttributesData('location', '', function(data) {
						if (data !== undefined) {
							fieldAttributesData['locations'] = data.results;
						}
					});
				} else if (attribute.format === 'org.openmrs.Drug') {
					getFieldAttributesData('drug', '', function(data) {
						if (data !== undefined) {
							fieldAttributesData['drugs'] = data.results;
						}
					});
				} else if (attribute.format === 'org.openmrs.Provider') {
					getFieldAttributesData('provider', '', function(data) {
						if (data !== undefined) {
							fieldAttributesData['providers'] = data.results;
						}
					});
				} else if (attribute.format === 'org.openmrs.ProgramWorkflow') {
					getFieldAttributesData('programworkflow', '', function(data) {
						if (data !== undefined) {
							fieldAttributesData['programworkflow'] = data.results;
						}
					});
				}
			}
		}

		function getFieldAttributesData(type, foreignKey, onLoadAttributeDataSuccessful) {
			var requestParams = [];
			requestParams['resource'] = FIELD_ATTRIBUTES_FRAGMENT_URL;
			requestParams['type'] = type;
			requestParams['foreignKey'] = foreignKey;
			EntityRestFactory.setCustomBaseUrl(ROOT_URL);
			EntityRestFactory.loadResults(requestParams,
				onLoadAttributeDataSuccessful, errorCallback);
		}

		function searchPerson(module_name, q, type) {
			var requestParams = [];

			requestParams['q'] = q;
			if (type === 'patient') {
				requestParams['v'] = "custom:(patientIdentifier:(uuid,identifier)," +
					"person:(personName))";
			}

			return EntityRestFactory.autocompleteSearch(requestParams, type, module_name, 'v1');
		}

		function loadStockRooms(rest_entity_name, successCallback) {
			var requestParams = {};
			requestParams['rest_entity_name'] = rest_entity_name;
			EntityRestFactory.loadEntities(requestParams, successCallback, errorCallback);
		}

		function searchItems(module_name, q) {
			EntityRestFactory.setBaseUrl(module_name);
		    var requestParams = {};
		    requestParams['q'] = q;
		    requestParams['limit'] = 10;
		    requestParams['startIndex'] = 1;
		    return EntityRestFactory.autocompleteSearch(requestParams, 'item');
		}



		function errorCallback(error) {
			emr.errorMessage(error);
		}
	}
})();
