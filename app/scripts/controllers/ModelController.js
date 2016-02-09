'use strict';

angular.module('kalafcheFrontendApp')
    .controller('ModelController', function($scope, ModelService, BrandService, DeviceTypeService) {

        init();

        function init() {
            $scope.model = {};
            $scope.brands = [];
            $scope.deviceTypes = [];

            getAllBrands();
            getAllDeviceTypes();
        }



        $scope.submitModel = function() {           
            ModelService.submitModel($scope.model);
            resetModelState();
        };

        function getAllBrands() {
            BrandService.getAllDeviceBrands().then(function(response) {
                $scope.brands = response;
            });

        };

        function getAllDeviceTypes() {
            DeviceTypeService.getAllDeviceTypes().then(function(response) {
                $scope.deviceTypes = response;
            });

        };

        function resetModelState() {
            $scope.model = {};
        }
    });