'use strict';

/**
 * @ngdoc function
 * @name kalafcheFrontendApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the kalafcheFrontendApp
 */
angular.module('kalafcheFrontendApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
