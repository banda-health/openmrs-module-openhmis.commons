(function() {
  'use strict';

  var baseModel = angular.module('app.genericMetadataModel');

  function GenericMetadataModel(GenericObjectModel) {

    var extended = angular.extend(GenericObjectModel, {});

    var defaultFields = extended.getModelFields();

    // @Override
    extended.getModelFields = function() {
      var fields = ["name", "description", "retireReason", "purge", "retired"];
      return fields.concat(defaultFields);
    };

    return extended;
  }

  baseModel.factory("GenericMetadataModel", GenericMetadataModel);
  GenericMetadataModel.$inject = ['GenericObjectModel'];
})();
