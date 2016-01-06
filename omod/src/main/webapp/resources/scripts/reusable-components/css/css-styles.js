(function() {
  'use strict';

  angular.module('app.css').factory('CssStylesFactory', CssStylesFactory);

  CssStylesFactory.$inject = [];

  function CssStylesFactory() {
    var service = {
      strikeThrough: strikeThrough
    }

    return service;
  }

  function strikeThrough(apply) {
    if (apply) {
      return {
        "text-decoration": "line-through"
      };
    } else {
      return {};
    }
  }
})();