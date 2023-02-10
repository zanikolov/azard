'use strict';

angular.module('kalafcheFrontendApp')
    .directive('model', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/product/model.html',
            controller: ModelController
        }
    });
  
    function ModelController($scope, ModelService, BrandService, ServerValidationService) {

        init();

        function init() {
            $scope.model = {};
            $scope.brands = [];
            $scope.models = [];
            $scope.currentPage = 1; 
            $scope.modelsPerPage = 10; 
            $scope.serverErrorMessages = {};

            getAllBrands();
            getAllModels();
        }

        $scope.submitModel = function() {         
            ModelService.submitModel($scope.model).then(function(response) {
                getAllModels();
                resetModelState();
                $scope.resetServerErrorMessages();
                $scope.modelForm.$setPristine();
                $scope.modelForm.$setUntouched();
            },
            function(errorResponse) {
                ServerValidationService.processServerErrors(errorResponse, $scope.modelForm);
                $scope.serverErrorMessages = errorResponse.data.errors;
            });
        };

        $scope.resetServerErrorMessages = function() {
            $scope.serverErrorMessages = {};
        };

        function getAllBrands() {
            BrandService.getAllBrands().then(function(response) {
                $scope.brands = response;
            });

        };

        function getAllModels() {
            ModelService.getAllModels().then(function(response) {
                $scope.models = response;
            });

        };

        function resetModelState() {
            var brandId = $scope.model.brandId;
            $scope.model = {};
            $scope.model.brandId = brandId;
        }

        $scope.resetModelName = function() {
            $scope.model.name = '';
        }

        $scope.cancelEditModel = function() {
            resetModelState();
            $scope.resetServerErrorMessages();
            $scope.modelForm.name.$setPristine();
            $scope.modelForm.name.$setUntouched();
        }

        $scope.editModel = function(selectedModel) {
            $scope.model = angular.copy(selectedModel);
        }
    };