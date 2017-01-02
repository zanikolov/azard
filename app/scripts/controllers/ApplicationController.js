'use strict';

angular.module('kalafcheFrontendApp')
	.controller('ApplicationController', function ($scope, $rootScope, UserRoles, AuthService, AuthEvents, SessionService) {

		init();

		function init() {
            $scope.userRoles = UserRoles;
		};

        $scope.logout = function() {
            AuthService.logout().then(function (response) {
                SessionService.destroy();
                console.log(">>>> 1");
                $rootScope.currentUser = {};
                console.log(">>>> 2");
                $rootScope.$broadcast(AuthEvents.logoutSuccess);
                console.log(">>>> 3");
            });
        }

        $rootScope.$on(AuthEvents.loginSuccess, function () {
			$scope.currentUser = SessionService.currentUser;
        })

	  	$scope.isAuthorized = function(roles) {
	  		return AuthService.isAuthorized(roles);
	  	};

	  	$scope.isAdmin = function() {
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
	  	};	

	  	$scope.isSuperAdmin = function() {
	  		var roles = SessionService.currentUser.userRoles;

	  		if (roles) {
		  		for (var i = 0; i < roles.length; i++) {
	                var role = roles[i];
	                if (role.name === "ROLE_SUPERADMIN") {
	                    return true;
	                }
	            }
			}

            return false;
	  	}; 
	});