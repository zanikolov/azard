'use strict';

angular.module('kalafcheFrontendApp')
	.service('BrandService', function($http) {
		angular.extend(this, {
			submitBrand: submitBrand,
            getAllDeviceBrands: getAllDeviceBrands
		});

    	function submitBrand(brand) {	
    		var config = {"headers" : {
    			"Access-Control-Allow-Origin" : "localhost:9000"
    			}
    		};
			return $http.post('http://localhost:8080/KalafcheBackend/service/deviceBrand/insertBrand', brand, config)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	)
    	}

        function getAllDeviceBrands() {   
            return $http.get('http://localhost:8080/KalafcheBackend/service/deviceBrand/getAllDeviceBrands')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});