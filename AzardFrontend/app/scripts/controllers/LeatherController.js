'use strict';

angular.module('kalafcheFrontendApp')

    .directive('leather', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/assortment/leather.html',
            controller: LeatherController
        }
    });

    function LeatherController ($scope, LeatherService, AuthService, ServerValidationService) {

        init();

        function init() {
            $scope.leather = {}; 
            $scope.leathers = [];
            $scope.currentPage = 1; 
            $scope.leathersPerPage = 20;
            $scope.serverErrorMessages = {};

            getAllLeathers();
        }

        $scope.resetLeаther = function() {
            $scope.leather = null;
        };

        function getAllLeathers() {
            LeatherService.getAllLeathers().then(function(response) {
                $scope.leathers = response;
            });
        };

        $scope.resetServerErrorMessages = function() {
            $scope.serverErrorMessages = {};
        };

        $scope.resetLeatherForm = function() {
            $scope.leather = null;
            $scope.resetServerErrorMessages();
            $scope.leatherForm.$setPristine();
            $scope.leatherForm.$setUntouched();
        };

        $scope.editLeather = function (leather) {
            $scope.leather = angular.copy(leather);
        };

        $scope.submitLeather = function() {
            LeatherService.submitLeather($scope.leather).then(function(response) {
                $scope.resetLeatherForm();
                getAllLeathers();
            },
            function(errorResponse) {
                ServerValidationService.processServerErrors(errorResponse, $scope.leatherForm);
                $scope.serverErrorMessages = errorResponse.data.errors;
            });
        };

    };