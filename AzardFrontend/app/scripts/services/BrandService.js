'use strict';

angular.module('kalafcheFrontendApp')
	.service('BrandService', function($http, Environment) {
		angular.extend(this, {
			submitBrand: submitBrand,
            getAllBrands: getAllBrands
		});

    	function submitBrand(brand) {	
			return $http.put(Environment.apiEndpoint + '/AzardBackend/brand', brand)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	)
    	}

        function getAllBrands() {  
            return $http.get(Environment.apiEndpoint + '/AzardBackend/brand')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }
	});