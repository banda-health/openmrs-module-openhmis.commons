(function() {
  'use strict';

  angular.module('app.cookies').service('CookiesService', CookiesService);

  CookiesService.$inject = ['$cookies'];

  function CookiesService($cookies) {
    var service;

    service = {
      set: set,
      get: get,
      remove: remove
    };

    return service;

    function set(key, value) {
      $cookies[key] = value;
    }

    function get(key) {
      return $cookies[key];
    }

    function remove(key) {
      delete $cookies[key];
    }
  }
})();
