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
        .state('newBrand', {
            url: '/new-brand',
            templateUrl: '/views/partials/partial-new-brand.html'
        }).state('newModel', {
            url: '/new-model',
            templateUrl: '/views/partials/partial-new-model.html'      
        }).state('newType', {
            url: '/new-type',
            templateUrl: '/views/partials/partial-new-type.html'      
        }).state('newStock', {
            url: '/new-stock',
            templateUrl: '/views/partials/partial-new-stock.html'      
        }).state('search', {
            url: '/search',
            templateUrl: '/views/partials/partial-search.html'      
        });
        
});
