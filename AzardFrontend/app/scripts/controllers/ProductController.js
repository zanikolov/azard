'use strict';

angular.module('kalafcheFrontendApp')

    .directive('product', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/assortment/item.html',
            controller: ItemController
        }
    });

    function ProductController ($scope, LeatherService, ItemService, BrandService, ModelService, AuthService, ServerValidationService) {

        init();

        function init() {
            $scope.item = {}; 
            $scope.items = [];
            $scope.currentPage = 1; 
            $scope.itemsPerPage = 20;
            
            $scope.serverErrorMessages = {};

            getAllLeathers();
            getAllBrands();
            getAllModels(); 
            getAllItems();
        }

        $scope.resetItem = function () {
            $scope.item = null;
        };

        function getAllLeathers() {
            LeatherService.getAllLeathers().then(function(response) {
                $scope.leathers = response;
            });
        };

        function getAllItems() {
            ItemService.getAllItems().then(function(response) {
                $scope.items = response;
            });
        };

        function getItemSpecificPrice(itemId) {
            ItemService.getItemSpecificPrice(itemId).then(function(response) {
                return response;
            });
        };

        $scope.resetServerErrorMessages = function() {
            $scope.serverErrorMessages = {};
        };

        $scope.editItem = function (item) {
            LeatherService.getItemSpecificPrice(item.id).then(function(response) {
                $scope.item = angular.copy(item);
                $scope.item.specificPrices = response;
                console.log($scope.item);
            });
        };

        $scope.resetItemForm = function() {
            $scope.resetItem();
            $scope.resetServerErrorMessages();
            $scope.productForm.$setPristine();
            $scope.productForm.$setUntouched();
        };

        $scope.submitItem = function() {
            ItemService.submitItem($scope.item).then(function(response) {
                $scope.resetItemForm();
                getAllItems();
            },
            function(errorResponse) {
                ServerValidationService.processServerErrors(errorResponse, $scope.itemForm);
                $scope.serverErrorMessages = errorResponse.data.errors;
            });
        };

        $scope.isSuperAdmin = function() {
            return AuthService.isSuperAdmin();
        }

        // $scope.filterByProductCode = function() {
        //     var productCodesString = $scope.productCode;
        //     var productCodes = productCodesString.split(" ");
        //     return function predicateFunc(product) {
        //         return productCodes.indexOf(product.code) !== -1 ;
        //     };
        // };

    };