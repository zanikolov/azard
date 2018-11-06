'use strict';

angular.module('kalafcheFrontendApp')
	.controller('WasteModalController', function ($scope, $mdDialog, selectedStock, ApplicationService, WasteService) {

        init();

        function init() {
            $scope.selectedStock = selectedStock;
            $scope.waste = {};
            $scope.submitInProcess = false;
        };

        $scope.submitWaste = function() {
            $scope.submitInProcess = true;
            var file = $scope.myFile;
            $scope.waste.itemId = $scope.selectedStock.itemId;
            WasteService.submitWaste($scope.waste, file).then(
                function(response) {
                    $scope.submitInProcess = false;
                    $scope.selectedStock.quantity -= 1;
                    $mdDialog.hide($scope.selectedStock);
                }, function(response) {
                    $scope.submitInProcess = false;
                }
            );
        };

		$scope.closeModal = function () {
			$mdDialog.cancel();
		};

	});