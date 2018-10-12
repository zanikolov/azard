'use strict';

angular.module('kalafcheFrontendApp')
	.service('ProductService', function($http, Environment, ApplicationService) {
		angular.extend(this, {
			submitProduct: submitProduct,
            updateProduct: updateProduct,
            getAllProducts: getAllProducts,
            validateProduct: validateProduct
		});

    	function submitProduct(product) {	
			return $http.post(Environment.apiEndpoint + '/KalafcheBackend/product/insertProduct', product)
            	.then(
                	function(response) {
                    	return response.data;
                	}
            	) 
    	}

        function updateProduct(product) { 
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/product/updateProduct', product)
                .then(
                    function(response) {
                        console.log(response);
                    }
                ) 
        }

        function getAllProducts() {   
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/product/getAllProducts')
                .then(
                    function(response) {
                        return response.data;
                    }
                ) ;
        }

        function validateProduct(product, products, productForm) {
            if (productForm.inputProductCode && !ApplicationService.validateProductCodeDuplication(product, products)) {
               productForm.inputProductCode.$invalid = true;

                return false;

            } else if (!ApplicationService.validateDuplication(product.name, products)) {
                console.log(products);
                productForm.inputName.$invalid = true;

                return false;

            } else if (!productForm.$valid) {
                if (productForm.inputProductCode) {
                    productForm.inputProductCode.$dirty = true;
                }
                productForm.inputName.$dirty = true;
                productForm.inputPrice.$dirty = true;
                productForm.inputPurchasePrice.$dirty = true;

                return false;

            } else  {
                return true;
            }
        };

	});