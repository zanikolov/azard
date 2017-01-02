'use strict';

angular.module('kalafcheFrontendApp')
    .controller('BrandController', function($scope, BrandService, ApplicationService) {
        
        init();

        function init() {
            $scope.newBrand = {};
            $scope.brands = [];
            $scope.isErrorMessageVisible = false;  
            $scope.errorMessage = "";  

            getAllDeviceBrands();
        }

        $scope.submitBrand = function() {
            if (ApplicationService.validateDuplication($scope.newBrand.name, $scope.brands)) {
                BrandService.submitBrand($scope.newBrand).then(function(response) {
                    $scope.brands.push($scope.newBrand);
                    resetBrandState();
                    $scope.isErrorMessageVisible = false; 
                    $scope.deviceBrandForm.$setPristine();
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Има въведена марка с това име";
            }
        };

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        function resetBrandState() {
            $scope.newBrand = {};
        }

        function getAllDeviceBrands() {
            BrandService.getAllDeviceBrands().then(function(response) {
                $scope.brands = response;
            });
        };
    });