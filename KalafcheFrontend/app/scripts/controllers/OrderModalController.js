'use strict';

angular.module('kalafcheFrontendApp')
	.controller('OrderModalController', function ($scope, $uibModalInstance, orderedStock, OrderedStockService) {

        init();

        function init() {
            $scope.orderedStock = orderedStock;
            $scope.isSubmitButonDisabled = false;
            if ($scope.orderedStock.orderedQuantity != 0) {
               $scope.quantityForOrder = $scope.orderedStock.orderedQuantity;
            }

            //Stock order
            console.log(orderedStock);
            //$scope.stockForOrder = selectedStock.selectedStock;
            //$scope.orderedStockList = selectedStock.orderedStockList;
        };

        $scope.submitOrderedStock = function() {
            var stock = $scope.orderedStock;
            var orderedStock = {"itemId": stock.itemId, "deviceModelId": stock.deviceModelId, "stockOrderId": stock.stockOrderId, "quantity": $scope.quantityForOrder};
            OrderedStockService.submitOrderedStock(orderedStock).then(
                function(response) {
                    console.log(">>>>>");
                    console.log(response);

                    //stock.orderedQuantity = $scope.quantityForOrder
                    $uibModalInstance.close($scope.quantityForOrder);
                }
            );
        };

		$scope.closeModal = function () {
			$uibModalInstance.dismiss('cancel');
		};

	});