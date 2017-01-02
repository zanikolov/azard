'use strict';
  
angular.module('kalafcheFrontendApp')
  .factory('AuthInterceptor', function ($rootScope, $q, AuthEvents) {
  return {
    responseError: function (response) { 
      $rootScope.$broadcast({
        401: AuthEvents.notAuthenticated,
        403: AuthEvents.notAuthorized
      }[response.status], response);
      return $q.reject(response);
    }
  };
})