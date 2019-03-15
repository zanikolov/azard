'use strict';

angular.module('kalafcheFrontendApp')
	.controller('ApplicationController', function ($scope, $rootScope, $mdSidenav, $mdDialog, UserRoles, AuthService, AuthEvents, SessionService, ApplicationService) {

	  init();

		function init() {
      $scope.userRoles = UserRoles;
      $scope.credentials = {};
      $scope.currentUser = SessionService.getCurrentUser();
		};

    $scope.logout = function() {
        AuthService.logout().then(function (response) {
            $scope.toggleSidenav('left');
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

      $scope.login = function() {
          AuthService.login($scope.credentials).then(function (response) {
                            console.log("---------");
              console.log(response.data);
              SessionService.create(response.data);
              $scope.currentUser = SessionService.currentUser;
              $rootScope.currentUser = SessionService.currentUser;
          }, function (response) {
              if (response.status === 401) {
                  $scope.loginForm.username.$invalid = true;
                  $scope.loginForm.password.$invalid = true;
              };
          });
      }

      $scope.toggleSidenav = function(menuId) {
        $mdSidenav(menuId).toggle();
      };

      $scope.menu = [
        {
          link : 'assortment',
          title: 'Асортимент',
          icon: 'dashboard'
        }
        ,{
          link : 'inStock',
          title: 'Търсене',
          icon: 'dashboard'
        },    
        {
          link : 'new-stock',
          title: 'Нова стока',
          icon: 'dashboard'
        },  
        {
          link : 'device',
          title: 'Устройства',
          icon: 'dashboard'
        },    
        {
          link : 'employee',
          title: 'Служители',
          icon: 'dashboard'
        },
        {
          link : 'kalafcheStore',
          title: 'Обекти',
          icon: 'dashboard'
        },
        {
          link : 'partner',
          title: 'Партньори',
          icon: 'dashboard'
        },
        {
          link : 'partnerStore',
          title: 'Обекти партньори',
          icon: 'dashboard'
        },
        {
          link : 'saleReport',
          title: 'Справки',
          icon: 'dashboard'
        },        
        {
          link : 'wasteReport',
          title: 'Брак',
          icon: 'dashboard'
        },        
        {
          link : 'refundReport',
          title: 'Рекламации',
          icon: 'dashboard'
        },
        {
          link : 'relocation',
          title: 'Релокации',
          icon: 'dashboard'
        },
        {
          link : 'activityReport',
          title: 'Активности',
          icon: 'dashboard'
        },
        {
          link : 'expense',
          title: 'Разходи',
          icon: 'dashboard'
        },
        {
          link : 'revision',
          title: 'Ревизии',
          icon: 'dashboard'
        },
        {
          link : 'discount',
          title: 'Промоции',
          icon: 'dashboard'
        }
      ];
	});