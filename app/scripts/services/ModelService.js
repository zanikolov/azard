'use strict';

angular.module('kalafcheFrontendApp')
	.service('ModelService', function($http) {
		angular.extend(this, {
			submitModel: submitModel,
            getAllDeviceModels: getAllDeviceModels
		});

    	function submitModel(model) {	
			return $http.post('http://localhost:8080/KalafcheBackend/service/deviceModel/insertModel', model)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	) 
    	}

        function getAllDeviceModels() {   
            return $http.get('http://localhost:8080/KalafcheBackend/service/deviceModel/getAllModelsGrouppedByBrand')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});