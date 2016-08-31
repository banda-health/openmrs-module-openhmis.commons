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
	
	EntityFunctions.$inject = ['$filter', '$timeout'];
	
	function EntityFunctions($filter, $timeout) {
		var service = {
			printPage: printPage,
			retireUnretireDeletePopup: retireUnretireDeletePopup,
			disableBackground: disableBackground,
			addExtraFormatListElements: addExtraFormatListElements,
			addAttributeType: addAttributeType,
			editAttributeType: editAttributeType,
			removeAttributeType: removeAttributeType,
			removeFromList: removeFromList,
			insertTemporaryId: insertTemporaryId,
			removeTemporaryId: removeTemporaryId,
			findIndexByKeyValue: findIndexByKeyValue,
			validateAttributeTypes: validateAttributeTypes,
			focusOnElement: focusOnElement,
		};

		return service;


		function printPage(url) {
			$("#printPage").remove();
			var printPage = $('<iframe id="printPage" src="' + url + '" width="1" height="1"></iframe>');
			printPage.load(function() {
				$(this).get(0).contentWindow.print();
			});
			$("body").append(printPage);
		}

		/**
		 * Show the retire/unretire and Delete popup
		 * @param selectorId - div id
		 */
		function retireUnretireDeletePopup(selectorId) {
			var dialog = emr.setupConfirmationDialog({
				selector: '#' + selectorId,
				actions: {
					cancel: function() {
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
		function disableBackground() {
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
						confirm: function() {
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
						cancel: function() {
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
				attributeOrder: attributeType.attributeOrder,
				foreignKey: attributeType.foreignKey,
				format: attributeType.format,
				name: attributeType.name,
				regExp: attributeType.regExp,
				required: attributeType.required,
			}

			$scope.attributeType = editAttributeType;
			$scope.editButton = emr.message['general.update'];
			$scope.addAttributeTypeTitle = '';
			var dialog = emr.setupConfirmationDialog({
				selector: '#attribute-types-dialog',
				actions: {
					confirm: function() {
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
					cancel: function() {
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

		/* We check the index of the attribute type in the attributeTypes array. The Attribute Type
		 * attributeOrder is always the same as index of the attribute type then compare an assign the
		 * attributeOrder*/
		function updateAttributeTypesOrder(attributeTypes) {
			for (var i = 0; i < attributeTypes.length; i++) {
				var attributeType = attributeTypes[i];
				if (attributeType != null) {
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
				for (var attributeType in attributeTypes) {
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
		 **/
		function findIndexByKeyValue(arrayToSearch, valueToSearch) {
			return arrayToSearch.indexOf($filter('filter')(arrayToSearch, {id: valueToSearch}, true)[0]);
		}
		
		// validate attribute types
		function validateAttributeTypes(attributeTypeAttributes, attributeValues, validatedAttributeTypes) {
			var failAttributeTypeValidation = false;
			if (attributeTypeAttributes !== undefined) {
				var count = 0;
				for (var i = 0; i < attributeTypeAttributes.length; i++) {
					var attributeType = attributeTypeAttributes[i];
					var required = attributeType.required;
					var requestAttributeType = {};
					var value = "";
					requestAttributeType['attributeType'] = attributeType.uuid;
					//get value
					if (attributeValues[attributeType.uuid] !== undefined) {
						value = attributeValues[attributeType.uuid].value;
					}

					// check if value is NOT set for required fields.
					if (required && (value === undefined || value === "")) {
						var errorMsg = $filter('EmrFormat')(emr.message("openhmis.commons.general.required.itemAttribute"), [attributeType.name]);
						emr.errorAlert(errorMsg);
						failAttributeTypeValidation = true;
					} else {
						if (value.id !== undefined) {
							requestAttributeType['value'] = value.id;
						} else {
							requestAttributeType['value'] = value.toString();
						}
						validatedAttributeTypes[count] = requestAttributeType;
						count++;
					}
				}
			}

			return !failAttributeTypeValidation;
		}

		function focusOnElement(elementId) {
			$timeout(function() {
				var elem = document.getElementById(elementId);
				elem.focus();
				elem.select();
			}, 100);
		}
	}

	app.directive('navigateTable', function() {
		return function(scope, element, attr){
			element.bind("keydown keypress", function(event) {
				if (event.which === 38) {
					var target = $(event.target).prev();
					$(target).trigger('focus');
				}
				if (event.which === 40) {
					var target = $(event.target).next();
					$(target).trigger('focus');
				}
			});
		}
	});

	app.directive('ngEnter', function($window) {
		return function(scope, element, attrs) {
			/**
			 * This function binds an on-enter event on any line item auto-complete
			 * search box and moves focus to the next available search box.
			 */
			element.bind("keydown keypress", function(event) {
				if (event.which === 13) {
					scope.$apply(function() {
						scope.$eval(attrs.ngEnter, {'event': event});
					});

					// retrieve all input elements
					var pageElems = document.querySelectorAll('input'),
						focusNext = false,
						len = pageElems.length;
					var foundSrc = false;
					for (var i = 0; i < len; i++) {
						var pe = pageElems[i];
						// check if the selected input element IS the source element
						// (i.e the element that triggered the event)
						if (pe === event.srcElement) {
							foundSrc = true;
						}

						// search for the next 'search' input element
						if (focusNext && pe !== event.srcElement && foundSrc) {
							if (pe.style.display !== 'none' && pe.id === "searchBox") {
								pe.focus();
								break;
							}
						} else if (pe.type === "image") {
							focusNext = true;
						}
					}
					event.preventDefault();
				}
			});
		};
	});

	app.directive('optionsDisabled', function($parse) {
		var disableOptions = function(scope, attr, element, data,
		                              fnDisableIfTrue) {
			// refresh the disabled options in the select element.
			var options = element.find("option");
			for (var pos = 0, index = 0; pos < options.length; pos++) {
				var elem = angular.element(options[pos]);
				if (elem.val() != "") {
					var locals = {};
					locals[attr] = data[index];
					elem.attr("disabled", fnDisableIfTrue(scope, locals));
					index++;
				}
			}
		};
		return {
			priority: 0,
			require: 'ngModel',
			link: function(scope, iElement, iAttrs, ctrl) {
				// parse expression and build array of disabled options
				var expElements = iAttrs.optionsDisabled.match(
					/^\s*(.+)\s+for\s+(.+)\s+in\s+(.+)?\s*/);
				var attrToWatch = expElements[3];
				var fnDisableIfTrue = $parse(expElements[1]);
				scope.$watch(attrToWatch, function(newValue, oldValue) {
					if (newValue)
						disableOptions(scope, expElements[2], iElement,
							newValue, fnDisableIfTrue);
				}, true);
				// handle model updates properly
				scope.$watch(iAttrs.ngModel, function(newValue, oldValue) {
					var disOptions = $parse(attrToWatch)(scope);
					if (newValue)
						disableOptions(scope, expElements[2], iElement,
							disOptions, fnDisableIfTrue);
				});
			}
		};
	});
})();
