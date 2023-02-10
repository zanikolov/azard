'use strict';

angular.module('kalafcheFrontendApp')

    .directive('item', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/assortment/item.html',
            controller: ItemController
        }
    });

    function ItemController ($scope, $element, ItemService, BrandService, ModelService, LeatherService, ApplicationService) {

    	init();

        function init() {
            $scope.items = [];
            $scope.currentPage = 1; 
            $scope.itemsPerPage = 20;
            $scope.selectedItem = {};

            getAllBrands();
            getAllLeathers();
            getAllModels(); 
            getAllItems();
        }

        function getAllBrands() {
            BrandService.getAllBrands().then(function(response) {
                $scope.brands = response;
            });
        };

        function getAllLeathers() {
            LeatherService.getAllLeathers().then(function(response) {
                $scope.products = response;
            });

        };

        function getAllModels() {
            ModelService.getAllModels().then(function(response) {
                $scope.models = response; 
            });
        };

        $scope.resetSelectedItem = function () {
            $scope.selectedItem = {};
        };

        function getAllItems() {
            ItemService.getAllItems().then(function(response) {
                $scope.items = response;
            });
        };

        $scope.editItem = function (item) {
            ItemService.getItemSpecificPrice(item.id).then(function(response) {
                $scope.selectedItem = angular.copy(item);
                $scope.selectedItem.specificPrices = response;
                console.log($scope.selectedItem);
            });
        };

        $scope.filterByProductCode = function() {
            var productCodesString = $scope.productCode;
            var productCodes = productCodesString.split(" ");
            return function predicateFunc(item) {
                return productCodes.indexOf(item.productCode) !== -1 ;
            };
        };

        $scope.clearModelSearchTerm= function() {
            $scope.modelSearchTerm = "";
        }

        $element.find('#modelSearchTerm').on('keydown', function(ev) {
            ev.stopPropagation();
        });

        $scope.$on('cancelEdit', function (event, data) {
            $scope.selectedItem = {};
        });

        $scope.$on('submitSuccess', function (event, data) {
            $scope.selectedItem = {};
            getAllItems();
        });

    };