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
