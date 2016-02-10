'use strict';

angular.module('kalafcheFrontendApp')
    .controller('NewStockController', function ($scope, ModelService, BrandService, ItemTypeService, ColorService) {

        init();

        function init() {
            $scope.itemsForStock = [];
            $scope.brands = [];
            $scope.itemTypes = [];
            $scope.models = [];
            $scope.colors = [];

            getAllBrands();
            getAllItemTypes();
            getAllDeviceModels();        
            getAllColors(); 
        }



        $scope.addItemForStock = function() {
            var newItem = {};
            $scope.itemsForStock.push(newItem);
        };

        $scope.deleteItemForStock = function(index) {
            $scope.itemsForStock.splice(index, 1);
            console.log($scope.itemsForStock);
        };

        $scope.submitNewStock = function() {
            console.log($scope.itemsForStock);
        };

        function getAllBrands() {
            BrandService.getAllDeviceBrands().then(function(response) {
                $scope.brands = response;
            });
        };

        function getAllItemTypes() {
            ItemTypeService.getAllItemTypes().then(function(response) {
                $scope.itemTypes = response;
            });

        };

        function getAllDeviceModels() {
            ModelService.getAllDeviceModels().then(function(response) {
                $scope.models = response;
                console.log($scope.models); 
            });
        };

        function getAllColors() {
            ColorService.getAllColors().then(function(response) {
                $scope.colors = response;
            });

        };
  });