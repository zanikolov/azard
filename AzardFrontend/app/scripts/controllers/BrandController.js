'use strict';

angular.module('kalafcheFrontendApp')
    .directive('brand', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/product/brand.html',
            controller: BrandController,
            controllerAs: 'vm'
        }
    });
  
    function BrandController($scope, BrandService, ServerValidationService) {
        var vm = this;
        init();

        function init() {
            $scope.brand = {};
            $scope.brands = [];
            $scope.serverErrorMessages = {};

            $scope.brandsPerPage = 10;
            $scope.currentPage = 1;

            getAllBrands();
        }

        $scope.submitBrand = function() {
            BrandService.submitBrand($scope.brand).then(
                function(response) {
                    getAllBrands();
                    resetBrandState();
                    $scope.resetServerErrorMessages();
                    $scope.brandForm.$setPristine();
                    $scope.brandForm.$setUntouched();
                },
                function(errorResponse) {
                    ServerValidationService.processServerErrors(errorResponse, $scope.brandForm);
                    $scope.serverErrorMessages = errorResponse.data.errors;
                });
        };

        $scope.resetServerErrorMessages = function() {
            $scope.serverErrorMessages = {};
        };

        function resetBrandState() {
            $scope.brand = {};
        }

        function getAllBrands() {
            BrandService.getAllBrands().then(function(response) {
                $scope.brands = response;
            });
        };
    };