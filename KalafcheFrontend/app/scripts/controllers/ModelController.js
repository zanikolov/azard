'use strict';

angular.module('kalafcheFrontendApp')
    .controller('ModelController', function($scope, ModelService, BrandService, DeviceTypeService, ApplicationService) {

        init();

        function init() {
            $scope.newModel = {};
            $scope.brands = [];
            $scope.deviceTypes = [];
            $scope.models = [];
            $scope.isErrorMessageVisible = false;  
            $scope.errorMessage = ""; 
            $scope.currentPage = 1; 
            $scope.modelsPerPage = 10; 

            getAllDeviceBrands();
            getAllDeviceTypes();
            getAllDeviceModels();
        }

        $scope.submitModel = function() {
            if (ApplicationService.validateDuplication($scope.newModel.name, $scope.models)) {           
                ModelService.submitModel($scope.newModel).then(function(response) {
                    $scope.models.push($scope.newModel);
                    resetModelState();
                    $scope.isErrorMessageVisible = false; 
                    $scope.deviceModelForm.$setPristine();
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Има въведен модел с това име";
            }
        };

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        function getAllDeviceBrands() {
            BrandService.getAllDeviceBrands().then(function(response) {
                $scope.brands = response;
            });

        };

        function getAllDeviceTypes() {
            DeviceTypeService.getAllDeviceTypes().then(function(response) {
                $scope.deviceTypes = response;
            });

        };

        function getAllDeviceModels() {
            ModelService.getAllDeviceModels().then(function(response) {
                $scope.models = response;
            });

        };

        function resetModelState() {
            $scope.newModel = {};
        }
    });