angular.module('kalafcheFrontendApp')
	.service('BrandService', function($http) {
		angular.extend(this, {
			submitBrand: submitBrand
		});

    	function submitBrand(brand) {	
    		var config = {"headers" : {
    			"Access-Control-Allow-Origin" : "kalafcheFrontendApp"
    			}
    		};
			return $http.post('http://52.28.122.198:8080/KalafcheBackend/service/deviceBrand/insertBrand', brand, config)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	) 
	    	}
	});