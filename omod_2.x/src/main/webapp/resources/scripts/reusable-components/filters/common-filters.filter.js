(function() {
  'use strict';

  /*
   * Interpolate messages with values. Parameters: input - string (e.g I am %s
   * module) and args - array of values (e.g ['openhmis']
   */
  angular.module('app.filters').filter('EmrFormat', function() {
    return function(input, args) {
      var output = '';
      var count = 0;
      var pattern = /%s/g;
      output = input.replace(pattern, function() {
        return args[count++];
      });
      return output;
    };
  });
})();