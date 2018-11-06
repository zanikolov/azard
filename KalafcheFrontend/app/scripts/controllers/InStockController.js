'use strict';

angular.module('kalafcheFrontendApp')
	.controller('InStockController', function ($scope, $mdDialog, $uibModal, ModelService, BrandService, ProductService, InStockService, SessionService, KalafcheStoreService) {

		init();

		function init() {
            $scope.currentPage = 1; 
            $scope.inStockPerPage = 15;
			$scope.inStockList = [];
            $scope.brands = [];
            $scope.products = [];
            $scope.models = [];
            $scope.kalafcheStores = [];
            $scope.productCode = "";
            $scope.selectedKalafcheStore = null;

            $scope.currentSale = {};
            $scope.currentSale.selectedStocks = [];

            getAllBrands();
            getAllProducts();
            getAllDeviceModels();         
            getAllKalafcheStores();
		}

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

        $scope.getAllInStock = function() {
            var userKalafcheStoreId = SessionService.currentUser.employeeKalafcheStoreId ? SessionService.currentUser.employeeKalafcheStoreId : 0;
            InStockService.getAllInStock(userKalafcheStoreId, $scope.selectedKalafcheStore.id).then(function(response) {
                $scope.inStockList = response;
                $scope.resetCurrentPage();
            });
        }

        function getAllKalafcheStores() {
            KalafcheStoreService.getAllKalafcheStores().then(function(response) {
                $scope.kalafcheStores = response;
                $scope.selectedKalafcheStore = {"id": SessionService.currentUser.employeeKalafcheStoreId};
                $scope.getAllInStock();
            });

        };

        $scope.filterByProductCode = function() {
            var productCodesString = $scope.productCode;
            var productCodes = productCodesString.split(" ");
            return function predicateFunc(inStock) {
                return productCodes.indexOf(inStock.productCode) !== -1 ;
            };
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

        $scope.getKalafcheStoreById = function(kalafcheStoreId) {
            var stores = $scope.kalafcheStores;
            for (var i = 0; i < stores.length; i++) {
                var currentStore = stores[i];
                if (currentStore.id === kalafcheStoreId) {
                    return currentStore.id;
                }
            }

            return null;
        };

	    $scope.openRelocationModal = function(stock){
            $scope.selectedStock = stock;

            // var modalInstance = $uibModal.open({
            //     animation: true,
            //     templateUrl: 'views/modals/relocation-modal.html',
            //     controller: 'RelocationModalController',
            //     size: "md",
            //     resolve: {
            //         selectedStock: function () {
            //                 return $scope.selectedStock;
            //             },
            //         selectedStore: function() {
            //                 return $scope.selectedKalafcheStore;
            //             }
            //         }
            //     });

            // modalInstance.result.then(function (selectedStock) {
            //         $scope.selectedStock = selectedStock;
            //     }, function () {
            //         console.log('Modal dismissed at: ' + new Date());
            //     }
            // );

            $mdDialog.show({
              locals:{selectedStock: $scope.selectedStock, selectedStore: $scope.selectedKalafcheStore},
              controller: 'RelocationModalController',
              templateUrl: 'views/modals/relocation-modal.html',
              parent: angular.element(document.body)
            })
            .then(function(answer) {
              $scope.status = 'You said the information was "".';
            }, function() {
              $scope.status = 'You cancelled the dialog.';
            });
	    };

        $scope.openSaleModal = function (stock) {
            if (stock) {
                $scope.currentSale.selectedStocks.push(stock);
                stock.quantity -= 1;
            }

            // var modalInstance = $uibModal.open({
            //     animation: true,
            //     templateUrl: 'views/modals/sale-modal.html',
            //     controller: 'SaleModalController',
            //     size: "md",
            //     resolve: {
            //         currentSale: function() {
            //                 return $scope.currentSale;
            //             }
            //         }
            //     });

            // modalInstance.result.then(function (currentSale) {
            //         $scope.currentSale = currentSale;
            //     }, function () {
            //         console.log('Modal dismissed at: ' + new Date());
            //     }
            // );

            $mdDialog.show({
              locals:{currentSale: $scope.currentSale},
              controller: 'SaleModalController',
              templateUrl: 'views/modals/sale-modal.html',
              parent: angular.element(document.body)
            })
            .then(function(answer) {
              $scope.status = 'You said the information was "".';
            }, function() {
              $scope.status = 'You cancelled the dialog.';
            });

        };

        $scope.openWasteModal = function(stock){
            $scope.selectedStock = stock;

            // var modalInstance = $uibModal.open({
            //     animation: true,
            //     templateUrl: 'views/modals/relocation-modal.html',
            //     controller: 'RelocationModalController',
            //     size: "md",
            //     resolve: {
            //         selectedStock: function () {
            //                 return $scope.selectedStock;
            //             },
            //         selectedStore: function() {
            //                 return $scope.selectedKalafcheStore;
            //             }
            //         }
            //     });

            // modalInstance.result.then(function (selectedStock) {
            //         $scope.selectedStock = selectedStock;
            //     }, function () {
            //         console.log('Modal dismissed at: ' + new Date());
            //     }
            // );

            $mdDialog.show({
              locals:{selectedStock: $scope.selectedStock},
              controller: 'WasteModalController',
              templateUrl: 'views/modals/waste-modal.html',
              parent: angular.element(document.body)
            })
            .then(function(answer) {
              $scope.status = 'You said the information was "".';
            }, function() {
              $scope.status = 'You cancelled the dialog.';
            });
        };


        $scope.resetCurrentPage = function() {
            $scope.currentPage = 1;
        };

        $scope.isEmployeeKalafcheStoreSelected = function(stock) { 
            return stock.kalafcheStoreId === SessionService.currentUser.employeeKalafcheStoreId;
        };

        $scope.isTotalSumRowVisible = function() {
            if ($scope.isAdmin() && $scope.inStockPerPage * $scope.currentPage >= $scope.inStockList.length) {
                return true;
            } else {
                return false;
            }
        };
        $scope.getTotalSum = function() {
            var totalSum = 0;

            for (var i = 0; i < $scope.inStockList.length; i++) {
                var currStock = $scope.inStockList[i];
                    totalSum += currStock.productPrice * currStock.quantity;               
            }

            return Math.round(totalSum * 100) / 100;
        };      
        
	});