'use strict';

angular.module('kalafcheFrontendApp')
	.service('NewStockService', function($http) {
		angular.extend(this, {
			submitNewStock: submitNewStock
		});

    	function submitNewStock(newStockList) {	
			return $http.post('http://localhost:8080/KalafcheBackend/service/deviceModel/insertModel', newStockList)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	) 
    	}

        // function getAllDeviceModels() {   
        //     return $http.get('http://localhost:8080/KalafcheBackend/service/deviceModel/getAllDeviceModels')
        //         .then(
        //             function(response) {
        //                 console.log(response);
        //             }
        //         ) 
        // }
	});