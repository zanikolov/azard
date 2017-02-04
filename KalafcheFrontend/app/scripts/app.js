'use strict';

/**
 * @ngdoc overview
 * @name kalafcheFrontendApp
 * @description
 * # kalafcheFrontendApp
 *
 * Main module of the application.
 */
angular
    .module('kalafcheFrontendApp', [
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'ui.router',
        'config',
        'angularUtils.directives.dirPagination',
        'ui.bootstrap'])
    .config(function($stateProvider, $urlRouterProvider, $httpProvider, UserRoles) {
        $httpProvider.defaults.withCredentials = true;
        $urlRouterProvider.otherwise('/login');
        
        $stateProvider
            .state('brand', {
                url: '/brand',
                templateUrl: 'views/partials/partial-brand.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }
            }).state('model', {
                url: '/new-model',
                templateUrl: 'views/partials/partial-model.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }     
            }).state('item', {
                url: '/item',
                templateUrl: 'views/partials/partial-item.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }      
            }).state('partner', {
                url: '/partner',
                templateUrl: 'views/partials/partial-partner.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin]
                }      
            }).state('employee', {
                url: '/employee',
                templateUrl: 'views/partials/partial-employee.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin]
                }      
            }).state('partnerStore', {
                url: '/partner-store',
                templateUrl: 'views/partials/partial-partner-store.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin]
                }      
            }).state('newStock', {
                url: '/new-stock',
                templateUrl: 'views/partials/partial-new-stock.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }      
            }).state('inStock', {
                url: '/in-stock',
                templateUrl: 'views/partials/partial-in-stock.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }      
            }).state('deviceType', {
                url: '/device-type',
                templateUrl: 'views/partials/partial-device-type.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }      
            }).state('saleReport', {
                url: '/sale-report',
                templateUrl: 'views/partials/partial-sale-report.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }      
            }).state('stockReport', {
                url: '/stock-report',
                templateUrl: 'views/partials/partial-stock-report.html',
                data: {
                    authorizedRoles: [ UserRoles.admin, UserRoles.superAdmin]
                }      
            }).state('stockRelocation', {
                url: '/stock-relocation',
                templateUrl: 'views/partials/partial-stock-relocation.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user]
                }      
            }).state('stockOrder', {
                url: '/stock-order',
                templateUrl: 'views/partials/partial-stock-order.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin]
                }      
            }).state('login',{
                url: '/login',
                templateUrl: 'views/partials/partial-login.html',
                data: {
                    authorizedRoles: []
                }
            });

        $httpProvider.interceptors.push(['$injector',
            function ($injector) {
                return $injector.get('AuthInterceptor');
            }
        ]);

        $httpProvider.interceptors.push(['$injector',
            function ($injector) {
                return $injector.get('SessionTimeoutInterceptor');
            }
        ]);

    })
    .run(function ($rootScope, $state, $http, AuthEvents, AuthService, SessionService) {
        $rootScope.$on('$stateChangeStart', function (event, next) {

            var authorizedRoles = next.data.authorizedRoles;
            if (!AuthService.isAuthorized(authorizedRoles)) {
                event.preventDefault();
                if (AuthService.isAuthenticated()) {
                    // user is not allowed
                    $rootScope.$broadcast(AuthEvents.notAuthorized);
                    $state.go('login')
                    console.log("Not authorized!");
                } else {
                    // user is not logged in
                    $rootScope.$broadcast(AuthEvents.notAuthenticated);
                }
            }
        });
        $rootScope.$on(AuthEvents.notAuthenticated, function () {
            SessionService.destroy();
            $state.go('login');
        });
        $rootScope.$on(AuthEvents.sessionTimeout, function () {
            SessionService.destroy();
            $state.go('login');
        });
        $rootScope.$on(AuthEvents.notAuthorized, function () {
            console.log("403 Forbidden");
        });
        $rootScope.$on(AuthEvents.loginSuccess, function () {
            $state.go('inStock');
        })
        $rootScope.$on(AuthEvents.logoutSuccess, function () {
            $state.go('login');
        })
    });
