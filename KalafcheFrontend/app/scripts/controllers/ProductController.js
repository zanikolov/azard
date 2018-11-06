'use strict';

angular.module('kalafcheFrontendApp')

    .directive('product', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/assortment/product.html',
            controller: ProductController
        }
    });

    function ProductController ($scope, ProductService, ServerValidationService) {

        init();

        function init() {
            $scope.product = {}; 
            $scope.products = [];
            $scope.currentPage = 1; 
            $scope.productsPerPage = 20;
            $scope.serverErrorMessages = {};

            getAllProducts();
        }

        $scope.resetProduct = function () {
            $scope.product = null;
        };

        function getAllProducts() {
            ProductService.getAllProducts().then(function(response) {
                $scope.products = response;
            });
        };

        function getProductSpecificPrice(productId) {
            ProductService.getProductSpecificPrice(productId).then(function(response) {
                return response;
            });
        };

        $scope.resetServerErrorMessages = function() {
            $scope.serverErrorMessages = false;
        };

        $scope.editProduct = function (product) {
            ProductService.getProductSpecificPrice(product.id).then(function(response) {
                $scope.product = angular.copy(product);
                $scope.product.specificPrices = response;
                console.log($scope.product);
            });

        };

        $scope.cancelEditProduct = function() {
            $scope.resetProduct();
            $scope.resetServerErrorMessages
            $scope.productForm.$setPristine();
        };

        $scope.submitProduct = function() {
            ProductService.submitProduct($scope.product).then(function(response) {
                getAllProducts();
                $scope.resetProduct();
                $scope.productForm.$setPristine();
            },
            function(errorResponse) {
                ServerValidationService.processServerErrors(errorResponse, $scope.productForm);
                $scope.serverErrorMessages = errorResponse.data.errors;
            });
        };

    };