'use strict';
  
angular.module('kalafcheFrontendApp')
    .service('AuthService', function ($http, $rootScope, $cookies, SessionService, AuthEvents, Environment) {
        angular.extend(this, {
            login: login,
            logout: logout,
            isAuthorized: isAuthorized,
            isAuthenticated: isAuthenticated,
            isAdmin: isAdmin
        });


        function login(credentials) {
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

        function isAdmin() {
            var roles = SessionService.currentUser.userRoles;

            if (roles) {
                for (var i = 0; i < roles.length; i++) {
                    var role = roles[i];

                    if (role.name === "ROLE_ADMIN" || role.name === "ROLE_SUPERADMIN") {
                        return true;
                    }
                }
            }

              return false;
        }   
 
  
    });
  