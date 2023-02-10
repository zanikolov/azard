'use strict';

angular.module('kalafcheFrontendApp')
	.controller('StockReportController', function ($scope, $uibModal, ModelService, BrandService, LeatherService, ColorService, InStockService, SessionService, StoreService, SaleService, PartnerService, ApplicationService, StockOrderService, OrderedStockService) {

		init();

		function init() {
            $scope.inStockCurrentPage = 1; 
            $scope.inStockPerPage = 10;
            $scope.orderedStockCurrentPage = 1; 
            $scope.orderedStockPerPage = 10;
			$scope.inStockList = [];
            $scope.brands = [];
            $scope.products = [];
            $scope.models = [];
            $scope.productCode = "";
            $scope.lessThan;
            $scope.selectedBrand = {};
            $scope.selectedModel = {};
            $scope.onlyOrdered = false;

            //Stock order
            $scope.stockOrder;
            $scope.orderedStockList = [];

            getAllBrands();
            getAllLeathers();
            getAllModels();        
			getAllInStock();
            getStockOrder();


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

		function getAllInStock() {
            InStockService.getAllInStockForReport().then(function(response) {
                $scope.inStockList = response;
            });
		}

        $scope.filterByProductCode = function() {
            var productCodesString = $scope.productCode;
            var productCodes = productCodesString.split(" ");
            return function predicateFunc(inStock) {
                return productCodes.indexOf(inStock.productCode) !== -1 ;
            };
        };

        $scope.filterByLessThan = function() {
            return function predicateFunc(inStock) {
                return $scope.lessThan > inStock.quantity;
            };
        };

        $scope.filterByOnlyOrdered = function() {
            return function predicateFunc(inStock) {
                if ($scope.onlyOrdered) {
                    return !(!inStock.orderedQuantity);
                } else {
                    return true;
                }
            };
        };

        $scope.resetCurrentPage = function() {
            $scope.inStockCurrentPage = 1;
            $scope.orderedStockCurrentPage = 1;
        };

        //Stock order
        $scope.createNewStockOrder = function() {
        };

        function getStockOrder() {
            StockOrderService.getCurrentStockOrder().then(function(stockOrder) {
                $scope.stockOrder = stockOrder.data;

                OrderedStockService.getAllOrderedStocks($scope.stockOrder.id).then(function(orderedStockList) { 
                    $scope.orderedStockList = orderedStockList;
                });
            });
        };

        $scope.addOrderedStock = function(inStock) {
            $scope.orderedStockList.push(inStock);
        };

        $scope.openOrderModal = function(inStock){

            inStock.stockOrderId = $scope.stockOrder.id;

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'stockOrderModal',
                controller: 'OrderModalController',
                size: "sm",
                resolve: {
                    orderedStock: function () {
                            return inStock;
                        }
                    }
                });

            modalInstance.result.then(function (orderedQuantity) {
                   inStock.orderedQuantity = orderedQuantity;
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                }
            );
        };
        
	});