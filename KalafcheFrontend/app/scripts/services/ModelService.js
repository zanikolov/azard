'use strict';

angular.module('kalafcheFrontendApp')
	.service('ModelService', function($http, Environment) {
		angular.extend(this, {
			submitModel: submitModel,
            getAllDeviceModels: getAllDeviceModels
		});

    	function submitModel(model) {	
			return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/deviceModel/insertModel', model)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	) 
    	}

        function getAllDeviceModels() {   
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/deviceModel/getAllDeviceModels')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});