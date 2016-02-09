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
    'ui.router'
  ]).config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/');
    
    $stateProvider
        .state('brand', {
            url: '/brand',
            templateUrl: '/views/partials/partial-brand.html'
        }).state('model', {
            url: '/new-model',
            templateUrl: '/views/partials/partial-model.html'      
        }).state('itemType', {
            url: '/item-type',
            templateUrl: '/views/partials/partial-item-type.html'      
        }).state('newStock', {
            url: '/new-stock',
            templateUrl: '/views/partials/partial-new-stock.html'      
        }).state('inStock', {
            url: '/in-stock',
            templateUrl: '/views/partials/partial-in-stock.html'      
        }).state('deviceType', {
            url: '/device-type',
            templateUrl: '/views/partials/partial-device-type.html'      
        });
        
});
