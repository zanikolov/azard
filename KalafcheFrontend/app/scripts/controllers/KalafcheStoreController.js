'use strict';

angular.module('kalafcheFrontendApp')
    .controller('KalafcheStoreController', function($scope, KalafcheStoreService, ApplicationService) {
        
        init();

        function init() {
            $scope.newKalafcheStore = {};
            $scope.kalafcheStores = [];
            $scope.isErrorMessageVisible = false;  
            $scope.errorMessage = ""; 
            $scope.currentPage = 1; 
            $scope.kalafcheStoresPerPage = 10; 

            getAllKalafcheStores();
        }

        $scope.submitKalafcheStore = function() {
            if (validateKalafcheStoreName()) {           
                KalafcheStoreService.submitKalafcheStore($scope.newKalafcheStore).then(function(response) {
                    $scope.kalafcheStores.push($scope.newKalafcheStore);
                    $scope.kalafcheStoreForm.$setPristine();
                    resetKalafcheStoreState();
                    $scope.isErrorMessageVisible = false; 
                    
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Съществува магазин с това име в " + $scope.newKalafcheStore.city;
            }
        };

        function validateKalafcheStoreName() {
            if (ApplicationService.validateDuplication($scope.newKalafcheStore.name, $scope.kalafcheStores)) {
                return true;
            } else {
                for (var i = 0; i < $scope.kalafcheStores.length; i++) {
                    if ($scope.newKalafcheStore.city === $scope.kalafcheStores[i].city) {
                        return false;
                    }
                }

                return true;
            }
        }

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        function getAllKalafcheStores() {
            KalafcheStoreService.getAllKalafcheStores().then(function(response) {
                $scope.kalafcheStores = response;
            });

        };

        function resetKalafcheStoreState() {
            $scope.newKalafcheStore = {};
        }
    });