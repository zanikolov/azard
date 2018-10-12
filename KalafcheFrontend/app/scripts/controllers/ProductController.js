'use strict';

angular.module('kalafcheFrontendApp')
    .controller('ProductController', function($scope, ProductService, ApplicationService) {

        init();

        function init() {
            $scope.newProduct = {};
            $scope.products = []; 
            $scope.selectedProduct = {}; 
            $scope.isNewProductRowVisible = false;
            $scope.isNameErrorMessageVisible = false;
            $scope.isCodeErrorMessageVisible = false;
            $scope.nameErrorMessage = "Има въведен артикул с това име"; 
            $scope.codeErrorMessage = "Има въведен артикул с този код"; 
            $scope.currentPage = 1;

            getAllProducts();
        }

        $scope.submitNewProduct = function() {
            if (ProductService.validateProduct($scope.newProduct, $scope.products, $scope.productFormTable.newProductFormRow)) {

                // var fd = new FormData();

                

                // var oBlob = new Blob(['test'], { type: "text/plain"});
                // fd.append("file", oBlob,'test.txt');

                // $scope.newProduct.pic = fd;

                ProductService.submitProduct($scope.newProduct).then(function(response) {
                    var insertedProduct = response;
                    insertedProduct.purchasePrice = Math.round((insertedProduct.purchasePrice * 1.956)*100)/100;
                    $scope.products.unshift(insertedProduct);
                    $scope.resetNewProduct();
                    $scope.productFormTable.newProductFormRow.$setPristine();
                });
            }  
        };

        $scope.updateEdittedProduct = function(index) {
            var productsWithoutSelected = angular.copy($scope.products);
            productsWithoutSelected.splice(index, 1);

            if (ProductService.validateProduct($scope.selectedProduct, productsWithoutSelected, $scope.productFormTable.editProductFormRow)) {
                ProductService.updateProduct($scope.selectedProduct).then(function(response) {
                    $scope.selectedProduct.purchasePrice = Math.round(($scope.selectedProduct.purchasePrice * 1.956)*100)/100;
                    $scope.products[index] = angular.copy($scope.selectedProduct);
                    $scope.selectedProduct = {};
                    $scope.productFormTable.editProductFormRow.$setPristine();
                });
            }  
        };

        $scope.getTemplate = function(product) {
            if ($scope.isSuperAdmin() && product && product.id === $scope.selectedProduct.id){
                return 'edit';
            } else {
                return 'display';
            }
        };

        $scope.addNewProductRow = function() {
            $scope.isNewProductRowVisible = true;
        };

        $scope.editProduct = function (product) {
            $scope.selectedProduct = angular.copy(product);
        };

        $scope.resetSelectedProduct = function () {
            $scope.selectedProduct = {};
        };

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        $scope.resetNewProduct = function() {
            $scope.isNewProductRowVisible = false;
            $scope.newProduct = {};
        };

        function getAllProducts() {
            ProductService.getAllProducts().then(function(response) {
                $scope.products = response;
            });
        };

        function getAllProducts() {
            ProductService.getAllProducts().then(function(response) {
                $scope.products = response;
            });
        };
    });