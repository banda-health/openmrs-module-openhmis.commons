(function() {
  'use strict';

  var baseModel = angular.module('app.paginateModel', []);

  function PaginateModel() {

    function Paginate(totalNumOfResults, numberOfPages, entities) {
      this.totalNumOfResults = totalNumOfResults;
      this.numberOfPages = numberOfPages;
      this.entities = entities;
    }

    Paginate.prototype = {

      getTotalNumOfResults: function() {
        return this.totalNumOfResults;
      },

      getNumberOfPages: function() {
        return this.numberOfPages;
      },

      getEntities: function() {
        return this.entities;
      }
    };

    return Paginate;
  }
  baseModel.factory("PaginateModel", PaginateModel);
  PaginateModel.$inject = [];
})();
