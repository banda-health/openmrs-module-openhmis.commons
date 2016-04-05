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

  var baseController = angular.module('app.genericManageController');

  function GenericManageController($scope, $filter, EntityRestFactory, PaginationService, CssStylesFactory,
          GenericMetadataModel, CookiesService) {

    var self = this;

    self.module_name = '';
    self.rest_entity_name = '';
    self.entity_name = '';
    self.rest_version = 'v2';

    self.currentPage = self.entity_name + 'currentPage';
    self.limit = self.entity_name + 'limit';
    self.includeRetired = self.entity_name + 'includeRetired';

    // protected
    self.getModelAndEntityName = self.getModelAndEntityName || function() {
      var msg = 'This method sets the required base parameters ';
      msg += '(i.e module_name, rest_entity_name, entity_name) and MUST be implemented ';
      msg += 'by the implementing controller. \n';
      msg += 'To bind the parameters, simply call self.bindBaseParameters from within ';
      msg += 'self.getModelAndEntityName and pass module_name and entity_name';
      console.log(msg);
    }

    // protected
    self.bindBaseParameters = function(module_name, rest_entity_name, entity_name, rest_version) {
      self.module_name = module_name;
      self.rest_entity_name = rest_entity_name;
      self.entity_name = entity_name;
      if(angular.isDefined(rest_version)){
        self.rest_version = rest_version;
      }
    }

    // public
    self.paginate = function(start) {
      CookiesService.set(self.currentPage, start);
      CookiesService.set(self.limit, $scope.limit);

      var params = PaginationService.paginateParams(CookiesService.get(self.currentPage), $scope.limit, CookiesService
              .get(self.includeRetired), $scope.searchField);
      params['rest_entity_name'] = self.rest_entity_name;
      PaginationService.paginate(params, self.onPaginateSuccess, self.onPaginateError);
    }

    // protected
    self.onPaginateSuccess = self.onPaginateSuccess || function(paginateModel) {
      $scope.fetchedEntities = paginateModel.getEntities();
      $scope.totalNumOfResults = paginateModel.getTotalNumOfResults();
      $scope.numberOfPages = paginateModel.getNumberOfPages();
    }

    // protected
    self.onPaginateError = self.onPaginateError || function(error) {
      console.error(error);
      emr.errorMessage(error);
    }

    // protected
    self.bindExtraVariablesToScope = self.bindExtraVariablesToScope || function() {
      // console.log('generic bind extra variables to scope');
    }

    // public
    self.updateContent = function() {
      CookiesService.set(self.includeRetired, $scope.includeRetired);
      self.paginate($scope.currentPage);
    }

    // protected
    self.loadPage = function() {
      // define and/or instantiate variables
      self.initialize();
      // load 1st page..
      self.paginate($scope.currentPage);
    }

    // public
    // navigate to entity page
    self.loadEntityPage = function(url) {
      window.location = url;
    }

    // protected
    self.initialize = function() {
      // initialize restful webservice..
      self.getModelAndEntityName();
      EntityRestFactory.setBaseUrl(self.module_name, self.rest_version);

      if (!angular.isDefined($scope.fetchedEntities)) {
        $scope.fetchedEntities = [];
      }

      if (!angular.isDefined($scope.searchField)) {
        $scope.searchField = '';
      }

      if (!angular.isDefined(CookiesService.get(self.currentPage))
              || (CookiesService.get(self.currentPage) === "undefined")) {
        $scope.currentPage = 1;
      } else {
        $scope.currentPage = CookiesService.get(self.currentPage);
      }

      if (!angular.isDefined(CookiesService.get(self.limit)) || (CookiesService.get(self.limit) === "undefined")) {
        $scope.limit = 10;
      } else {
        $scope.limit = CookiesService.get(self.limit);
      }
      if (!angular.isDefined($scope.numberOfPages)) {
        $scope.numberOfPages = 0;
      }
      if (!angular.isDefined($scope.totalNumOfResults)) {
        $scope.totalNumOfResults = 0;
      }

      if (!angular.isDefined(CookiesService.get(self.includeRetired))) {
        $scope.includeRetired = false;
        CookiesService.set(self.includeRetired, $scope.includeRetired);
      } else {
        $scope.includeRetired = (CookiesService.get(self.includeRetired) === 'true');
      }

      $scope.updateContent = self.updateContent;
      $scope.loadEntityPage = self.loadEntityPage;
      $scope.paginate = self.paginate;

      $scope.pagingFrom = PaginationService.pagingFrom;
      $scope.pagingTo = PaginationService.pagingTo;
      $scope.strikeThrough = CssStylesFactory.strikeThrough;

      self.bindExtraVariablesToScope();

      $scope.newEntityLabel = $filter('EmrFormat')(emr.message("openhmis.inventory.general.new"), [self.entity_name]);
    }

    // load page..
    self.loadPage();
  }
  baseController.GenericManageController = GenericManageController;
})();
