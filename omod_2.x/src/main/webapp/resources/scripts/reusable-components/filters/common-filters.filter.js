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
