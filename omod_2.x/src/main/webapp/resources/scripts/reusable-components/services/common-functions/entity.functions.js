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
			disableBackground: disableBackground,
			addExtraFormatListElements: addExtraFormatListElements,
			addAttributeType: addAttributeType,
			editAttributeType: editAttributeType,
			removeAttributeType: removeAttributeType,
			removeFromList: removeFromList,
			insertTemporaryId: insertTemporaryId,
			removeTemporaryId: removeTemporaryId,
			findIndexByKeyValue: findIndexByKeyValue
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
			disableBackground();
		}

		/**
		 * Disable and gray-out background when a dialog box opens up.
		 */
		function disableBackground(){
			var backgroundElement = angular.element('.simplemodal-overlay');
			backgroundElement.addClass('disable-background');
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

		/**
		 * Displays a popup dialog box with the attribute types . Saves the attributeType on clicking the 'Ok' button
		 * @param $scope
		 */
		function addAttributeType($scope) {
			$scope.saveButton = emr.message['general.save'];
			var dialog = emr
				.setupConfirmationDialog({
					selector: '#attribute-types-dialog',
					actions: {
						confirm: function () {
							$scope.entity.attributeTypes = $scope.entity.attributeTypes
								|| [];
							$scope.submitted = true;
							if (angular.isDefined($scope.attributeType)
								&& $scope.attributeType.name !== "" && $scope.attributeType.format !== "") {
								$scope.entity.attributeTypes
									.push($scope.attributeType);
								insertTemporaryId(
									$scope.entity.attributeTypes,
									$scope.attributeType);
								updateAttributeTypesOrder($scope.entity.attributeTypes);
								$scope.attributeType = {};
							}
							$scope.$apply();
							dialog.close();
						},
						cancel: function () {
							dialog.close();
						}
					}
				});

			dialog.show();
		}

		/**
		 * Opens a popup dialog box to edit an attribute Type
		 * @param attributeType
		 * @param ngDialog
		 * @param $scope
		 */
		function editAttributeType(attributeType, $scope) {
			var tmpAttributeType = attributeType;

			var editAttributeType = {
				attributeOrder : attributeType.attributeOrder,
				foreignKey : attributeType.foreignKey,
				format : attributeType.format,
				name : attributeType.name,
				regExp : attributeType.regExp,
				required : attributeType.required,
			}

			$scope.attributeType = editAttributeType;
			$scope.editButton = emr.message['general.update'];
			$scope.addAttributeTypeTitle = '';
			var dialog = emr.setupConfirmationDialog({
				selector: '#attribute-types-dialog',
				actions: {
					confirm: function () {
						tmpAttributeType.attributeOrder = $scope.attributeType.attributeOrder;
						tmpAttributeType.foreignKey = $scope.attributeType.foreignKey;
						tmpAttributeType.format = $scope.attributeType.format;
						tmpAttributeType.name = $scope.attributeType.name;
						tmpAttributeType.regExp = $scope.attributeType.regExp;
						tmpAttributeType.required = $scope.attributeType.required;
						$scope.$apply();

						updateAttributeTypesOrder($scope.entity.attributeTypes);
						$scope.attributeType = {};
						dialog.close();
					},
					cancel: function () {
						$scope.attributeType = {};
						dialog.close();
					}
				}
			});

			dialog.show();
		}

		/**
		 * Removes an attribute Type from the list
		 * @param attribute Type
		 * @param attribute Types
		 */
		function removeAttributeType(attributeType, attributeTypes) {
			removeFromList(attributeType, attributeTypes);
			updateAttributeTypesOrder(attributeTypes);
		}

		/**
		 * Searches an attribute type and removes it from the list
		 * @param attribute type
		 * @param attribute Types
		 */
		function removeFromList(attributeType, attributeTypes) {
			var index = attributeTypes.indexOf(attributeType);
			if (index != -1) {
				attributeTypes.splice(index, 1);
			}
		}

		/*We check the index of the attribute type in the attributeTypes array. The Attribute Type
		 * attributeOrder is always the same as index of the attribute type then compare an assign the
		 * attributeOrder */
		function updateAttributeTypesOrder(attributeTypes){
			for(var i = 0; i < attributeTypes.length; i++){
				var attributeType = attributeTypes[i];
				if(attributeType != null) {
					if (attributeType.attributeOrder != i) {
						attributeType.attributeOrder = i;
					}
				}
			}
		}
		
		/**
		 * ng-repeat requires that every item have a unique identifier.
		 * This function sets a temporary unique id for all attribute types in the list.
		 * @param operationTypes (attributeTypes)
		 * @param operationType - optional
		 */
		function insertTemporaryId(attributeTypes, attributeType) {
			var rand = Math.floor((Math.random() * 99) + 1);
			if (angular.isDefined(attributeType)) {
				var index = attributeTypes.indexOf(attributeType);
				attributeType.id = index * rand;
			} else {
				for ( var attributeType in attributeTypes) {
					var index = attributeTypes.indexOf(attributeType);
					attributeType.id = index * rand;
				}
			}
		}
		
		/**
		 * Remove the temporary unique id from all operation types (attributetypes) before submitting.
		 * @param items
		 */
		function removeTemporaryId(attributeTypes) {
			for (var index in attributeTypes) {
				var attributeType = attributeTypes[index];
				delete attributeType.id;
			}
		}
		
		/**
		 * Gets the Index of a given element in an array
		 * @params arrayToSearch
		 * @params key
		 * @params valueToSearch
		 * @returns index || null
		 * */
		function findIndexByKeyValue(arrayToSearch, key, valueToSearch) {
			for (var i = 0; i < arrayToSearch.length; i++) {
				if (arrayToSearch[i][key] == valueToSearch) {
					return i;
				}
			}
			return null;
		}
	}

	app.directive('ngEnter', function ($window) {
		return function (scope, element, attrs) {
			element.bind("keydown keypress", function (event) {
				if (event.which === 13) {
					scope.$apply(function () {

						scope.$eval(attrs.ngEnter, {'event' : event});
					});

					event.preventDefault();
				}
			});
		};
	});

})();
