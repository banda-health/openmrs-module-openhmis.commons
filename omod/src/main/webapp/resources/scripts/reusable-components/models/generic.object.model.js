(function() {
  'use strict';

  var baseModel = angular.module('app.genericMetadataModel');

  /* Core functions */
  function GenericObjectModel() {

    var self = this;

    self.getModelFields = function() {
      return ["uuid"];
    }

    self.populateModels = function(data) {
      var entities = [];
      for (var i = 0; i < data.length; i++) {
        var entity = self.populateModel(data[i]);
        entities.push(entity);
      }

      return entities;
    };

    self.populateModel = function(data) {
      var entity = {};
      if (angular.isDefined(data)) {
        var fields = self.getModelFields();
        for (var i = 0; i < fields.length; i++) {
          var field = fields[i];
          if (angular.isDefined(data[field])) {
            entity[field] = data[field];
          } else {
            entity[field] = '';
          }
        }
      }

      return entity;
    };

    self.newModelInstance = function() {
      var fields = self.getModelFields();
      var entity = {};
      for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        entity[field] = '';
      }
      return entity;
    };

    return self;
  }

  baseModel.factory("GenericObjectModel", GenericObjectModel);
})();
