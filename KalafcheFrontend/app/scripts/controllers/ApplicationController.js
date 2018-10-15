'use strict';

angular.module('kalafcheFrontendApp')
	.controller('ApplicationController', function ($scope, $rootScope, UserRoles, AuthService, AuthEvents, SessionService, ApplicationService) {

		init();

		function init() {
            $scope.userRoles = UserRoles;
		};

        $scope.logout = function() {
            AuthService.logout().then(function (response) {
                SessionService.destroy();
                $rootScope.currentUser = {};
                $rootScope.$broadcast(AuthEvents.logoutSuccess);
            });
        }

        $rootScope.$on(AuthEvents.loginSuccess, function () {
			$scope.currentUser = SessionService.currentUser;
        })

	  	$scope.isAuthorized = function(roles) {
	  		return AuthService.isAuthorized(roles);
	  	}

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
	  	}	

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
	  	}

	  	$scope.convertEpochToDate = function(epochTime) {

            if(epochTime != 0) {
                var timeStamp = new Date(epochTime);

                var minutes = ApplicationService.getTwoDigitNumber(timeStamp.getMinutes());
                var hh = ApplicationService.getTwoDigitNumber(timeStamp.getHours());
                var dd = ApplicationService.getTwoDigitNumber(timeStamp.getDate());
                var mm = ApplicationService.getTwoDigitNumber(timeStamp.getMonth() + 1);
                var yyyy = timeStamp.getFullYear();

                return dd + '-' + mm + '-' + yyyy + ' ' + hh + ':' + minutes;
            } else {
                return '';
            }
        }; 
	});