'use strict';

angular.module('kalafcheFrontendApp')
	.service('BrandService', function($http, Environment) {
		angular.extend(this, {
			submitBrand: submitBrand,
            getAllDeviceBrands: getAllDeviceBrands
		});

    	function submitBrand(brand) {	
			return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/deviceBrand/insertBrand', brand)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	)
    	}

        function getAllDeviceBrands() {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/deviceBrand/getAllDeviceBrands')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }
	});