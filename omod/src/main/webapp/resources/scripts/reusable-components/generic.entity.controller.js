(function() {
  'use strict';

  var baseController = angular.module('app.genericEntityController');

  function GenericEntityController($scope, $filter, $stateParams, EntityRestFactory, GenericMetadataModel) {
    var self = this;
    self.module_name = '';
    self.rest_entity_name = '';
    self.entity_name = '';
    self.uuid = '';
    self.cancel_page = '';

    // protected
    self.setRequiredInitParameters = self.setRequiredInitParameters || function() {
      var msg = 'This method sets the required base parameters ';
      msg += '(i.e module_name, rest_entity_name, entity_name) and MUST be implemented ';
      msg += 'by the implementing controller. \n';
      msg += 'To bind the parameters, simply call self.bindBaseParameters from within ';
      msg += 'self.setRequiredInitParameters and pass module_name and entity_name';
      console.log(msg);
    }

    // protected
    self.bindBaseParameters = function(module_name, entity_name, cancel_page) {
      self.module_name = module_name;
      self.rest_entity_name = entity_name.toLowerCase();
      self.entity_name = entity_name;
      self.cancel_page = cancel_page;
    }

    // protected
    self.bindEntityToScope = self.bindEntityToScope || function(scope, entity) {
      scope.entity = entity;
    }

    // protected
    self.getUuid = self.getUuid || function() {
      return $stateParams['uuid'];
    }

    self.saveOrUpdate = self.saveOrUpdate || function() {
      var params = {};
      params = self.appendBaseParams(params);
      EntityRestFactory.saveOrUpdateEntity(params, $scope.entity, self.onChangeEntitySuccessful, self.onChangeEntityError);
    }

    self.retireOrUnretireCall = self.retireOrUnretireCall
            || function(retire) {
              var params = {};
              params = self.appendBaseParams(params);
              EntityRestFactory.retireOrUnretireEntity(params, $scope.entity, self.onChangeEntitySuccessful,
                      self.onChangeEntityError);
            }

    self.purge = self.purge || function() {
      var params = {};
      params = self.appendBaseParams(params);
      $scope.entity.purge = true;
      EntityRestFactory.purgeEntity(params, $scope.entity, self.onPurgeEntitySuccessful, self.onChangeEntityError);
    }

    self.loadEntity = self.loadEntity || function(uuid) {
      if (angular.isDefined(uuid) && uuid !== "") {
        var params = {
          uuid: uuid
        };
        params = self.appendBaseParams(params);
        EntityRestFactory.loadEntity(params, self.onLoadEntitySuccessful, self.onLoadEntityError);
      } else {
        var entity = GenericMetadataModel.newModelInstance();
        self.bindEntityToScope($scope, entity);
      }
    }

    /* #### START CALLBACK Methods #### */
    self.onChangeEntitySuccessful = self.onChangeEntitySuccessful || function(data) {
      self.cancel();
    }

    self.onChangeEntityError = self.onChangeEntityError || function(error) {
      emr.errorMessage(error);
    }

    self.onPurgeEntitySuccessful = self.onPurgeEntitySuccessful || function(data) {
      self.cancel();
    }

    self.onLoadEntitySuccessful = self.onLoadEntitySuccessful || function(data) {
      var entity = GenericMetadataModel.populateModel(data);
      self.bindEntityToScope($scope, entity);
      self.bindExtraVariablesToScope(entity.uuid);
    }

    self.onLoadEntityError = self.onLoadEntityError || function(error) {
      var entity = GenericMetadataModel.newModelInstance();
      self.bindEntityToScope($scope, entity);
      var msg = $filter('EmrFormat')(emr.message("openhmis.inventory.general.error.notFound"), [self.entity_name]);
      emr.errorMessage(msg + ":::" + error);
    }
    /* #### END CALLBACK Methods #### */

    self.cancel = self.cancel || function() {
      window.location = self.cancel_page;
    }

    self.bindExtraVariablesToScope = self.bindExtraVariablesToScope
            || function(uuid) {
              if (angular.isDefined($scope.entity) && angular.isDefined($scope.entity.retired)
                      && $scope.entity.retired === true) {
                $scope.retireOrUnretire = $filter('EmrFormat')(emr.message("openhmis.inventory.general.unretire"),
                        [self.entity_name]);
              } else {
                $scope.retireOrUnretire = $filter('EmrFormat')(emr.message("openhmis.inventory.general.retire"),
                        [self.entity_name]);
              }
            }

    self.appendBaseParams = self.appendBaseParams || function(params) {
      if (params) {
        params['rest_entity_name'] = self.rest_entity_name;
        return params;
      }
    }

    self.initialize = self.initialize
            || function() {
              self.uuid = self.getUuid();
              self.setRequiredInitParameters();

              if (!angular.isDefined(self.module_name) || !angular.isDefined(self.rest_entity_name)) {
                console
                        .log("Ensure that the module_name, rest_entity_name and cancel_page parameters have been set in the self.setRequiredInitParameters method.");
                return;
              }

              EntityRestFactory.setBaseUrl(self.module_name);
              $scope.cancel = self.cancel;
              $scope.purge = self.purge;
              $scope.saveOrUpdate = self.saveOrUpdate;
              $scope.retireOrUnretireCall = self.retireOrUnretireCall;
              self.bindExtraVariablesToScope('');

              // load messages..
              var messageLabels = self.loadMessageLabels();
              $scope.messageLabels = messageLabels;
            }

    self.loadPage = self.loadPage || function() {
      self.initialize();
      self.loadEntity(self.uuid);
    }

    self.loadMessageLabels = self.loadMessageLabels
            || function() {
              var messages = {};
              messages['delete.forever'] = $filter('EmrFormat')(emr.message("openhmis.inventory.general.delete"),
                      [self.entity_name]);
              messages['general.name'] = emr.message("general.name");
              messages['general.description'] = emr.message("general.description");
              messages['general.cancel'] = emr.message("general.cancel");
              messages['general.save'] = emr.message("general.save");
              messages['openhmis.inventory.general.retired.reason'] = emr
                      .message("openhmis.inventory.general.retired.reason");
              messages['general.retireReason'] = emr.message("general.retireReason");
              messages['general.purge'] = emr.message("general.purge");

              if (self.uuid === null || self.uuid === undefined || self.uuid === "") {
                messages['h2SubString'] = $filter('EmrFormat')(emr.message("openhmis.inventory.general.new"),
                        [self.entity_name]);
              } else {
                messages['h2SubString'] = emr.message("general.edit") + ' ' + self.entity_name;
              }

              return messages;
            }

    /* ENTRY POINT */
    self.loadPage();
  }

  baseController.GenericEntityController = GenericEntityController;
})();
