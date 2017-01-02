'use strict';

angular.module('kalafcheFrontendApp')
	.controller('InStockController', function ($scope, $uibModal, ModelService, BrandService, ItemService, ColorService, InStockService, SessionService, KalafcheStoreService, SaleService, PartnerService, ApplicationService) {

		init();

		function init() {
            $scope.currentPage = 1; 
            $scope.inStockPerPage = 15;
			$scope.showSaleModal = false;
			$scope.inStockList = [];
            $scope.brands = [];
            $scope.items = [];
            $scope.models = [];
            $scope.colors = [];
            $scope.kalafcheStores = [];
            $scope.partners = [];
            $scope.productCode = "";
            $scope.selectedBrand = {};
            $scope.selectedModel = {};
            $scope.selectedKalafcheStore = {};
            $scope.selectedStock = {};
            $scope.showSubmitSaleError = false;
            $scope.submitSaleErrorText = "";

            getAllBrands();
            getAllItems();
            getAllDeviceModels();        
            //getAllPartners(); 
            getAllKalafcheStores();
			getAllInStock();


		}

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
                $scope.selectedKalafcheStore = KalafcheStoreService.getSelectedKalafcheStore($scope.kalafcheStores, $scope.isAdmin());
            });

        };

		function getAllInStock() {
            InStockService.getAllInStock().then(function(response) {
                $scope.inStockList = response;
            });
		}

        $scope.filterByProductCode = function() {
            var productCodesString = $scope.productCode;
            var productCodes = productCodesString.split(" ");
            return function predicateFunc(inStock) {
                return productCodes.indexOf(inStock.itemProductCode) !== -1 ;
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
        
        // $scope.openSaleModal = function(stock){
        //     $scope.showSaleModal = true;
        //     $scope.selectedStock = stock;
        // };

	    $scope.openRelocationModal = function(stock){
            $scope.selectedStock = stock;

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'relocationModal',
                controller: 'StockModalController',
                size: "md",
                resolve: {
                    selectedStock: function () {
                            return $scope.selectedStock;
                        }
                    }
                });

            modalInstance.result.then(function (selectedStock) {
                    $scope.selectedStock = selectedStock;
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                }
            );
	    };

        $scope.openSaleModal = function (stock) {
            $scope.selectedStock = stock;

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'saleModal',
                controller: 'StockModalController',
                size: "md",
                resolve: {
                    selectedStock: function () {
                            return $scope.selectedStock;
                        }
                    }
                });

            modalInstance.result.then(function (selectedStock) {
                    $scope.selectedStock = selectedStock;
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                }
            );
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
                    totalSum += currStock.itemPrice * currStock.quantity;               
            }

            return Math.round(totalSum * 100) / 100;
        };

        // $scope.submitSale = function() {
        //     if ($scope.partnerCode) {
        //         if (!$scope.discountPercentage) {
        //             $scope.submitSaleErrorText = "Въведете процент на отстъпката!";
        //             $scope.showSubmitSaleError = true;
        //             console.log("Incorrect discount percentage!!!");
        //         } else {
        //          PartnerService.getPartnerByCode($scope.partnerCode).then(
        //              function(partner) {
        //                  if (partner) {  
        //                      var stock = $scope.selectedStock;
        //                      var sale = {"stockId": stock.id, "partnerId": partner.id, "employeeId": SessionService.currentUser.employeeId, "cost": stock.itemPrice, "discountPercentage": $scope.discountPercentage, "saleTimestamp": ApplicationService.getCurrentTimestamp()};
        //                      SaleService.submitSale(sale).then(
        //                          function(response) {
        //                              $scope.selectedStock.quantity -= 1;
        //                              $scope.closeModal();
        //                          }
        //                      );
        //                  } else{
        //                         $scope.submitSaleErrorText = "Несъществуващ код!";
        //                         $scope.showSubmitSaleError = true;
        //                      console.log("Incorrect partner's code!!!");
        //                  }
        //              }
        //          );
        //         }
        //     } else {
        //         var stock = $scope.selectedStock;
        //         var sale = {"stockId": stock.id, "employeeId": SessionService.currentUser.employeeId, "cost": stock.itemPrice, "discountPercentage": $scope.discountPercentage, "saleTimestamp": ApplicationService.getCurrentTimestamp()};
        //         SaleService.submitSale(sale).then(
        //             function(response) {
        //                 $scope.selectedStock.quantity -= 1;
        //                 $scope.closeModal();
        //             }
        //         );
        //     }
            
        // };
        
        
	});