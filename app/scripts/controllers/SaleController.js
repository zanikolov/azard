angular.module('kalafcheFrontendApp')
    .controller('SaleController', function($scope) {
        $scope.submitSale = function() {
            console.log(">>>>>>>>>>>> 1");
            $scope.closeModal();
        };

        $scope.closeModal = function() {
            $scope.showModal = false;
        }
    });