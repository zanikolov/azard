'use strict';
  
angular.module('kalafcheFrontendApp')
    .service('AuthService', function ($http, $rootScope, $cookies, SessionService, AuthEvents, Environment) {
        angular.extend(this, {
            login: login,
            logout: logout,
            isAuthorized: isAuthorized,
            isAuthenticated: isAuthenticated
        });


        function login(credentials) {
            console.log(">>>>>>>> " + Environment.apiEndpoint);
            var authorizationHeader = 'Basic ' + btoa(credentials.username + ':' + credentials.password);
            //$http.defaults.headers.common['Authorization'] = authorizationHeader;
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/employee/getEmployee', 
                    {headers: {'Authorization': authorizationHeader}});
        };

        function logout() {
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/logout/logout');
        };

        function isAuthenticated() {
            if (!!SessionService.currentUser.userId) {
                return true;
            } else {
                var currentUser = $cookies.getObject("currentUser");

                if (currentUser && currentUser.userId) {
                    SessionService.currentUser = currentUser;
                    $rootScope.currentUser = currentUser;

                    return true;
                } else {
                    return false;
                }
            }
        };
 
        function isAuthorized(authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                authorizedRoles = [authorizedRoles];
            }

            if (authorizedRoles.length == 0) {
                return true;
            }

            if (!isAuthenticated()) {
                return false;
            }

            var roles = SessionService.currentUser.userRoles;

            for (var i = 0; i < roles.length; i++) {
                var role = roles[i];

                if (authorizedRoles.indexOf(role.name) !== -1) {
                    return true;
                }
            };

            return false;
        };
 
  
    });
  