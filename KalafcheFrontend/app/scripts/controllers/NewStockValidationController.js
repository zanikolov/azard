'use strict';

angular.module('kalafcheFrontendApp')
    .directive('newStockValidation', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/new-stock/validation.html',
            controller: NewStockValidationController
        }
    });

    function NewStockValidationController($scope, $mdDialog, ModelService, BrandService, ProductService, NewStockService) {

        init();

        function init() {
            $scope.unexistingItems = [];
        };

        $scope.validateFile = function () {
            var file = $scope.myFile;
            NewStockService.validateFile(file).then(function(response) {
                $scope.unexistingItems = response; ;
            });
        };

        $scope.openCreateItemModal = function (item) {
            $scope.selectedItem = item;

            // var modalInstance = $uibModal.open({
            //     animation: true,
            //     templateUrl: '/views/partials/new-stock-validation-modal.html',
            //     controller: function ($scope, $uibModalInstance, selectedItem){

            //         $scope.selectedItem = selectedItem;

            //         $scope.closeModal = function() {
            //             $uibModalInstance.dismiss('cancel');
            //         }

            //         $scope.$on('submitSuccess', function (event, data) {
            //             $scope.selectedItem.added = true;
            //             $uibModalInstance.dismiss('cancel');
            //         });
            //     },
            //     size: "lg",
            //     resolve: {
            //         selectedItem: function () {
            //                 return $scope.selectedItem;
            //             }
            //         }
            //     });

            // modalInstance.result.then(function (selectedItem) {
            //         $scope.selectedItem = selectedItem;
            //     }, function () {
            //         console.log('Modal dismissed at: ' + new Date());
            //     }
            // );






                $mdDialog.show({
                    locals:{selectedItem: $scope.selectedItem},
                    controller: function ($scope, $mdDialog, selectedItem){

                        $scope.selectedItem = selectedItem;

                        $scope.closeModal = function() {
                            $mdDialog.cancel();
                        }

                        $scope.$on('submitSuccess', function (event, data) {
                            $scope.selectedItem.added = true;
                            $mdDialog.cancel();
                        });
                    },
                    templateUrl: 'views/partials/new-stock/validation-modal.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose:true
                })
                .then(function(selectedItem) {
                    $scope.selectedItem = selectedItem;
                }, function() {
                  $scope.status = 'You cancelled the dialog.';
                });




        };

  };