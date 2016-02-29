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
