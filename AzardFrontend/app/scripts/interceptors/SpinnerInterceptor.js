 'use strict';
  
angular.module('kalafcheFrontendApp')
    .factory('SpinnerInterceptor', function ($q, $window) {
        return function (promise) {
            return promise.then(function (response) {
                // do something on success
                // todo hide the spinner
                //alert('stop spinner');
                $('#spinner').hide();
                
                return response;

            }, function (response) {
                // do something on error
                // todo hide the spinner
                //alert('stop spinner');
                $('#spinner').hide();
                return $q.reject(response);
            });
        };
    });