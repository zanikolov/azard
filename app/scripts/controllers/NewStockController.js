'use strict';

angular.module('kalafcheFrontendApp')
    .controller('NewStockController', function ($scope, ModelService, BrandService, DeviceTypeService) {

        init();

        function init() {
            $scope.itemsForStock = [];
            $scope.brands = [];
            $scope.deviceTypes = [];
            $scope.models = [];

            getAllBrands();
            getAllDeviceTypes();
            getAllDeviceModels();         
        }



        $scope.addItemForStock = function() {
            var newItem = {};
    	       $scope.itemsForStock.push(newItem);
        };

        $scope.deleteItemForStock = function(index) {
    	       $scope.itemsForStock.splice(index, 1);
    	       console.log($scope.itemsForStock);
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

        function getAllDeviceModels() {
            ModelService.getAllDeviceModels().then(function(response) {
                $scope.models = response;
                console.log($scope.models); 
            });

        };
  });