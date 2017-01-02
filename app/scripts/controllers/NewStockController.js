'use strict';

angular.module('kalafcheFrontendApp')
    .controller('NewStockController', function ($scope, ModelService, BrandService, ItemService, ColorService, NewStockService, SessionService, KalafcheStoreService) {

        init();

        function init() {
            $scope.selecModelDisabled = true;

            $scope.newStocks = [];
            $scope.stocksWaitingForApproval = [];
            $scope.stocksWaitingForApprovalByKalafcheStore = [];
            $scope.deletedStocks = [];
            $scope.brands = [];
            $scope.items = [];
            $scope.models = [];
            $scope.colors = [];
            $scope.kalafcheStores = [];
            $scope.selectedKalafcheStore = {};

            getAllBrands();
            getAllItems();
            getAllDeviceModels();        
            getAllColors(); 
            getAllKalafcheStores();
        };

        $scope.addNewStock = function() {
            var newStock = {};
            newStock.approved = false;
            newStock.kalafcheStoreId = $scope.selectedKalafcheStore.id;
            newStock.approver = 1;
            $scope.newStocks.push(newStock);
        };

        $scope.deleteStockWaitingForApproval = function(index) {
            var deletedStock = $scope.stocksWaitingForApprovalByKalafcheStore[$scope.selectedKalafcheStore.id][index];
            $scope.deletedStocks.push(deletedStock);

            $scope.stocksWaitingForApprovalByKalafcheStore[$scope.selectedKalafcheStore.id].splice(index, 1);
        };

        $scope.deleteNewAddedStock = function(index) {
            $scope.newStocks.splice(index, 1);
        };

        $scope.submitNewStockForApproval = function() {
            var stocks = {
                "addedStocksForApproval": $scope.newStocks,
                "deletedStocksForApproval": $scope.deletedStocks

            }

            console.log($scope.newStocks);

            NewStockService.submitNewStockForApproval(stocks).then(
                    function(response) {
                        getStocksWaitingForApproval();
                        $scope.newStocks = response;
                        $scope.deletedStocks = [];
                    }
                );
            
        };
            

        $scope.approveStockForKalafcheStore = function() {
            var stocks = $scope.stocksWaitingForApprovalByKalafcheStore[$scope.selectedKalafcheStore.id];

            NewStockService.approveNewStock(stocks).then(
                    function(response) {
                        $scope.stocksWaitingForApprovalByKalafcheStore[$scope.selectedKalafcheStore.id] = [];
                    }
                );
        };

        function getAllBrands() {
            BrandService.getAllDeviceBrands().then(function(response) {
                $scope.brands = response;
            });
        };

        function getAllItems() {
            ItemService.getAllItems().then(function(response) {
                $scope.items = response;
            });

        };

        function getAllDeviceModels() {
            ModelService.getAllDeviceModels().then(function(response) {
                $scope.models = response; 
            });
        };

        function getAllColors() {
            ColorService.getAllColors().then(function(response) {
                $scope.colors = response;
            });

        };

        function getAllKalafcheStores() {
            KalafcheStoreService.getAllKalafcheStores().then(function(response) {
                $scope.kalafcheStores = response;              
                if ($scope.isAdmin()) {
                    $scope.selectedKalafcheStore = $scope.kalafcheStores[0];
                } else {
                    $scope.selectedKalafcheStore = KalafcheStoreService.getSelectedKalafcheStore($scope.kalafcheStores, $scope.isAdmin());
                }
                getStocksWaitingForApproval();
            });

        };

        function getStocksWaitingForApproval() {
            NewStockService.getStocksWaitingForApproval().then(function(response) {
                $scope.stocksWaitingForApproval = response;
                sortStocksForApprovalByKalafcheStore();
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

        function sortStocksForApprovalByKalafcheStore() {
            var stores = $scope.kalafcheStores;
            var stocks = $scope.stocksWaitingForApproval;

            for (var i = 0; i < stores.length; i++) {
                var store = stores[i];
                var stocksForStore = [];

                for (var j = 0; j < stocks.length; j++) {
                    var stock = stocks[j];

                    if (stock.kalafcheStoreId === store.id) {
                        stocksForStore.push(stock);
                    }
                }

                $scope.stocksWaitingForApprovalByKalafcheStore[store.id] = stocksForStore;
            }
        };

        $scope.onBrandChange = function(index) {
            var stock = $scope.newStocks[index];
            stock.quantityInStock = null;


            $scope.newStockForm.newStockFormRow.selectModel.$dirty = false;

            if (stock.deviceBrandId == null) {
                stock.deviceModelId = null;
            }
        };

        $scope.onModelChange = function(index) {
            var stock = $scope.newStocks[index];
            stock.quantityInStock = null;

            if (stock.deviceBrandId && stock.deviceModelId && stock.itemName) {
                NewStockService.getQuantityInStock(stock.itemId, stock.deviceModelId).then(function(response) {
                    stock.quantityInStock = response;
                });
            }
        };


        $scope.getItemProperties = function (stock) {
            stock.quantityInStock = null;

            for (var i = 0; i < $scope.items.length; i++) {
                var curr = $scope.items[i];

                if (stock.itemProductCode === curr.productCode) {
                    stock.itemId = curr.id;
                    stock.itemName = curr.name;
                    stock.itemPrice = curr.price;

                    break;
                }
            }

            if (stock.deviceBrandId && stock.deviceModelId && stock.itemName) {
                NewStockService.getQuantityInStock(stock.itemId, stock.deviceModelId).then(function(response) {
                    stock.quantityInStock = response;
                });
            }
        };

        $scope.resetItemProperties = function (stock) {
            stock.itemId = null;
            stock.itemName = null;
            stock.itemPrice = null;
            stock.quantityInStock = null;
        };
  });