'use strict';

angular.module('kalafcheFrontendApp')
    .directive('newStockImport', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/new-stock/import.html',
            controller: NewStockImportController
        }
    });

    function NewStockImportController($http, $scope, ModelService, BrandService, ProductService, NewStockService, KalafcheStoreService, ServerValidationService) {

        init();

        function init() {
            $scope.selecModelDisabled = true;
            $scope.newStockFormVisible = false;
            $scope.addNewStockButtonVisible = true;

            $scope.newNewStock = {};
            $scope.newStocks = [];
            $scope.brands = [];
            $scope.products = [];
            $scope.models = [];
            $scope.unexistingItems = [];

            $scope.newStocksPerPage = 20;
            $scope.currentPage = 1;

            getAllBrands();
            getAllProducts();
            getAllDeviceModels();
            getAllNewStocks();
        };

        $scope.addNewStock = function() {
            $scope.newStockFormVisible = true;
            $scope.addNewStockButtonVisible = false;
        };

        $scope.cancelAdditionOfNewStock = function() {
            $scope.newStockFormVisible = false;
            $scope.addNewStockButtonVisible = true;
            $scope.newNewStock = {};
        };

        $scope.submitNewAddedStock = function() {
            if ($scope.newStockForm.$valid) {
                NewStockService.submitNewStock($scope.newNewStock).then(function(response) {
                    NewStockService.getAllNewStocks().then(function(response) {
                        $scope.newStockFormVisible = false;
                        $scope.newNewStock = {};
                        $scope.newStocks = response;  
                        $scope.addNewStockButtonVisible = true;               
                    });
                });
            }
        };

        $scope.deleteNewStock = function(newStockId, index) {
            NewStockService.deleteNewStock(newStockId).then(
                    function(response) {
                        $scope.newStocks.splice(index, 1);
                    }
                );
            
        };

        $scope.approveNewStock = function(newStock, index) {
            NewStockService.approveNewStock(newStock).then(
                    function(response) {
                        $scope.newStocks.splice(index, 1);
                    }
                );     
        };

        $scope.approveAllNewStocks = function() {
            NewStockService.approveAllNewStocks($scope.newStocks).then(
                    function(response) {
                        $scope.newStocks = [];
                    }
                );     
        };

        $scope.printStickersForNewStocks = function() {
            NewStockService.printStickersForNewStocks($scope.newStocks).then(
                    function(response) {
                    }
                );     
        };

        function getAllBrands() {
            BrandService.getAllDeviceBrands().then(function(response) {
                $scope.brands = response;
            });
        };

        function getAllProducts() {
            ProductService.getAllProducts().then(function(response) {
                $scope.products = response;
            });

        };

        function getAllDeviceModels() {
            ModelService.getAllDeviceModels().then(function(response) {
                $scope.models = response; 
            });
        };

        function getAllNewStocks() {
            NewStockService.getAllNewStocks().then(function(response) {
                $scope.newStocks = response; 
            });
        };


        $scope.getNameById = function(list, id) {
            if (list) {
                for (var i = 0; i < list.length; i++) {
                    var current = list[i];
                    if (current.id === id) {
                        return current.name;
                    }
                }
            }

            return null;
        };

        $scope.isApproveButtonVisible = function() {
            return  $scope.isAdmin() && $scope.stocksWaitingForApprovalByKalafcheStore[$scope.selectedKalafcheStore.id].length > 0;
        };

        $scope.getProductProperties = function () {
            for (var i = 0; i < $scope.products.length; i++) {
                var curr = $scope.products[i];

                if ($scope.newNewStock.productCode === curr.code) {
                    $scope.newNewStock.productId = curr.id;
                    $scope.newNewStock.productName = curr.name;
                    $scope.newNewStock.productPrice = curr.price;

                    break;
                }
            }
        };

        $scope.resetProductProperties = function () {
            $scope.newNewStock.productId = null;
            $scope.newNewStock.productName = null;
        };

        $scope.importFile = function () {
            var file = $scope.file;
            NewStockService.importFile(file).then(function(response) {
                getAllNewStocks(); 
            },
            function(errorResponse) {
                ServerValidationService.processServerErrors(errorResponse, $scope.fileForm);
                $scope.serverErrorMessages = errorResponse.data.errors;
            });
        };
  };