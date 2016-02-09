'use strict';

angular.module('kalafcheFrontendApp')
    .controller('BrandController', function($scope, BrandService) {
        $scope.brand = {};

        $scope.submitBrand = function() {
            BrandService.submitBrand($scope.brand);
            resetBrandState();
        };

        function resetBrandState() {
            $scope.brand = {};
        }

        $scope.getAllDeviceBrands = function() {
            BrandService.getAllDeviceBrands();
        };
    });