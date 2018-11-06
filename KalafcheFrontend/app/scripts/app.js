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
        'ui.bootstrap',
        'ngMaterial'])
    .config(function($mdThemingProvider, $stateProvider, $urlRouterProvider, $httpProvider, UserRoles) {
        $httpProvider.defaults.withCredentials = true;
        $urlRouterProvider.otherwise('/login');
        
        $stateProvider
            // .state('brand', {
            //     url: '/brand',
            //     templateUrl: 'views/partials/partial-brand.html',
            //     data: {
            //         authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user],
            //         title: "Марки"
            //     }
            .state('device', {
                url: '/device',
                templateUrl: 'views/partials/device/device-tab.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user],
                    title: "Устройства"
                }
            }).state('assortment', {
                url: '/assortment',
                templateUrl: 'views/partials/assortment/assortment-tab.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user],
                    title: "Устройства"
                }
            }).state('employee', {
                url: '/employee',
                templateUrl: 'views/partials/partial-employee.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin],
                    title: "Служители"
                }      
            }).state('kalafcheStore', {
                url: '/kalafcheStore',
                templateUrl: 'views/partials/partial-kalafche-store.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin],
                    title: "Обекти"
                }      
            }).state('partner', {
                url: '/partner',
                templateUrl: 'views/partials/partial-partner.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin],
                    title: "Партньори"
                }      
            }).state('partnerStore', {
                url: '/partner-store',
                templateUrl: 'views/partials/partial-partner-store.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin],
                    title: "Обекти партньори"
                }      
            }).state('new-stock', {
                url: '/new-stock',
                templateUrl: 'views/partials/new-stock/new-stock-tab.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user],
                    title: "Нова стока"
                }      
            }).state('inStock', {
                url: '/in-stock',
                templateUrl: 'views/partials/partial-in-stock.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user],
                    title: "Наличности"
                }      
            }).state('saleReport', {
                url: '/sale-report',
                templateUrl: 'views/partials/sale-report/sale-report-tab.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user],
                    title: "Справки"
                }      
            }).state('stockReport', {
                url: '/stock-report',
                templateUrl: 'views/partials/partial-stock-report.html',
                data: {
                    authorizedRoles: [ UserRoles.admin, UserRoles.superAdmin],
                    title: "Справки наличности"
                }      
            }).state('wasteReport', {
                url: '/waste-report',
                templateUrl: 'views/partials/waste-report.html',
                data: {
                    authorizedRoles: [ UserRoles.admin, UserRoles.superAdmin],
                    title: "Брак"
                }      
            }).state('relocation', {
                url: '/rrelocation',
                templateUrl: 'views/partials/partial-relocation.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin, UserRoles.user],
                    title: "Релокации"
                }      
            }).state('stockOrder', {
                url: '/stock-order',
                templateUrl: 'views/partials/partial-stock-order.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin],
                    title: "Поръчки"
                }      
            }).state('activityReport', {
                url: '/activity-report',
                templateUrl: 'views/partials/activity-report.html',
                data: {
                    authorizedRoles: [UserRoles.superAdmin, UserRoles.admin],
                    title: "Активности"
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

        $httpProvider.interceptors.push('LoadingInterceptor');

        $httpProvider.interceptors.push(['$injector',
            function ($injector) {
                return $injector.get('SessionTimeoutInterceptor');
            }
        ]);
      
        $mdThemingProvider.theme('default').primaryPalette('purple')
        .accentPalette('yellow');
    })
    .run(function ($rootScope, $state, $http, AuthEvents, AuthService, SessionService) {
        $rootScope.$on('$stateChangeStart', function (event, next) {
            if (next.url == '/login') {
                $rootScope.sideNavVisible = false;
            } else {
                $rootScope.sideNavVisible = true;
            }

            $rootScope.pageTitle = next.data.title;
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
            $rootScope.sideNavVisible = true;
            $state.go('inStock');
        })
        $rootScope.$on(AuthEvents.logoutSuccess, function () {
            $state.go('login');
        })

    });
