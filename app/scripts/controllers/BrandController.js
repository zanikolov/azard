angular.module('kalafcheFrontendApp')
    .controller('BrandController', function($scope, BrandService) {
        $scope.brandName = '';

        $scope.submitBrand = function() {
            var name = $scope.brandName;
            var brand = {
                "name": name
            };

            BrandService.submitBrand(brand);
        };
    });