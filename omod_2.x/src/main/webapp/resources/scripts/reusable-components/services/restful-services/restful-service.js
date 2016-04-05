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

  angular.module('app.restfulServices').service('RestfulService', RestfulService);

  RestfulService.$inject = ['Restangular'];

  function RestfulService(Restangular) {
    var service;

    service = {
      setBaseUrl: setBaseUrl,
      all: all,
      one: one,
      remove: remove,
      saveOrUpdate: saveOrUpdate,
      post: customPOST
    };

    return service;

    function setBaseUrl(restWsUrl) {
      if (!angular.isUndefined(restWsUrl)) {
        Restangular.setBaseUrl(restWsUrl);
      }
    }

    /*
     * Retrieve a List of Objects: Note: Using
     * Restangular.all(resource).getList() requires the response to be an ARRAY.
     * This is NOT always the case, therefore customGET has been used instead.
     */
    function all(resource, request, successCallback, errorCallback) {
      Restangular.all(resource).customGET('', request).then(function(data) {
        if (typeof successCallback === 'function') {
          successCallback(data);
        }
      }, function(error) {
        if (typeof errorCallback === 'function') {
          commonErrorHandling(errorCallback, '', request, error);
        }
      });
    }

    /*
     * Retrieve ONLY one result.
     */
    function one(resource, uuid, successCallback, errorCallback) {
      Restangular.one(resource, uuid).customGET('/').then(function(data) {
        if (typeof successCallback === 'function') successCallback(data);
      }, function(error) {
        if (typeof errorCallback === 'function') {
          commonErrorHandling(errorCallback, uuid, params, error);
        }
      });
    }

    function saveOrUpdate(resource, uuid, request, successCallback, errorCallback) {
      customPOST(resource, uuid, request, successCallback, errorCallback);
    }

    function remove(resource, uuid, request, successCallback, errorCallback) {
      Restangular.one(resource, uuid).remove(request).then(function(data) {
        if (typeof successCallback === 'function') {
          successCallback(data);
        }
      }, function(error) {
        if (typeof errorCallback === 'function') {
          commonErrorHandling(errorCallback, uuid, request, error);
        }
      });
    }

    function customPOST(resource, uuid, request, successCallback, errorCallback) {
      var params = JSON.stringify(request);

      Restangular.one(resource, uuid).customPOST(params).then(function(data) {
        if (typeof successCallback === 'function') successCallback(data);
      }, function(error) {
        if (typeof errorCallback === 'function') {
          commonErrorHandling(errorCallback, uuid, params, error);
        }
      });
    }

    // Display error info on the console.
    function commonErrorHandling(errorCallback, uuid, parameters, error) {
      if (angular.isDefined(uuid)) {
        console.log("ERROR:::uuid - " + uuid);
      }
      if (angular.isDefined(parameters)) {
        console.log("ERROR:::Parmaters - " + parameters);
      }
      console.log("ERROR:::Status code - " + error.status);
      console.log("ERROR:::Status Text - " + error.statusText);
      console.log("ERROR:::Message - " + error.data.error.message);
      console.log("ERROR:::Detail - " + error.data.error.detail);

      errorCallback(error.data.error.message);
    }
  }
})();
