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

    function NewStockImportController($http, $scope, AuthService, SessionService, ModelService, BrandService, LeatherService, NewStockService, StoreService, ItemService, ServerValidationService) {

        init();

        function init() {
            $scope.selecModelDisabled = true;
            $scope.newStockFormVisible = false;
            $scope.addNewStockButtonVisible = true;

            $scope.newNewStock = {};
            $scope.newStocks = [];
            $scope.brands = [];
            $scope.leathers = [];
            $scope.models = [];
            $scope.unexistingItems = [];

            $scope.newStocksPerPage = 20;
            $scope.currentPage = 1;

            getAllBrands();
            getAllLeathers();
            getAllModels();
            getAllStores();
        };

        $scope.isAdmin = function() {
            return AuthService.isAdmin();
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
            if ($scope.newStockForm.$valid && $scope.selectedStore) {
                NewStockService.submitNewStock($scope.newNewStock, $scope.selectedStore.id).then(function(response) {
                    NewStockService.getNewStocks($scope.selectedStore.id).then(function(response) {
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
            BrandService.getAllBrands().then(function(response) {
                $scope.brands = response;
            });
        };

        function getAllLeathers() {
            LeatherService.getAllLeathers().then(function(response) {
                $scope.leathers = response;
            });

        };

        function getAllModels() {
            ModelService.getAllModels().then(function(response) {
                $scope.models = response; 
            });
        };

        function getNewStocks() {
            if ($scope.selectedStore) {
                NewStockService.getNewStocks($scope.selectedStore.id).then(function(response) {
                    $scope.newStocks = response; 
                });
            }
        };

        $scope.getNewStocks = function() {
            getNewStocks();
        }

        function getAllStores() {
            StoreService.getAllStores().then(function(response) {
                $scope.stores = response;
                if (AuthService.isAdmin() && $scope.stores != null && $scope.stores.length > 0) {
                    $scope.selectedStore = $scope.stores[0];
                } else {
                    $scope.selectedStore = {"id": SessionService.currentUser.employeeStoreId};
                }
                getNewStocks();
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
            return  $scope.isAdmin() && $scope.stocksWaitingForApprovalByStore[$scope.selectedStore.id].length > 0;
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

        $scope.importFile = function (file) {          
            if ($scope.selectedStore && $scope.selectedStore.id != 0 && file) {
                NewStockService.importFile(file, $scope.selectedStore.id).then(function(response) {
                    getNewStocks($scope.selectedStore.id); 
                },
                function(errorResponse) {
                    ServerValidationService.processServerErrors(errorResponse, $scope.fileForm);
                    $scope.serverErrorMessages = errorResponse.data.errors;
                });
            }
        };

        $scope.checkIfItemExists = function(newNewStock) {
            if (newNewStock.brandId != null && newNewStock.modelId != null && newNewStock.leatherId != null) {
                ItemService.checkIfItemExists(newNewStock).then(
                    function(response) {
                        console.log(">>> test");
                        console.log(response);
                        if (response) {
                            $scope.newNewStock.price = response.price;
                        } else {
                            
                            $scope.newNewStock.price = null;
                        }
                    });
            }        
        }
  };