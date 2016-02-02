angular.module('kalafcheFrontendApp')
  .controller('StockController', function ($scope) {
    $scope.itemsForStock = ["1", "2"];

    $scope.addItemForStock = function() {
    	$scope.itemsForStock.push("3");
    }

    $scope.deleteItemForStock = function(index) {
    	$scope.itemsForStock.splice(index, 1);
    	console.log($scope.itemsForStock);
    }
  });