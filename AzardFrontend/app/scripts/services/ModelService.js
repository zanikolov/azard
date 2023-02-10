'use strict';

angular.module('kalafcheFrontendApp')
	.service('ModelService', function($http, Environment) {
		angular.extend(this, {
			submitModel: submitModel,
            getAllModels: getAllModels
		});

   //  	function submitModel(model) {	
			// return $http.put(Environment.apiEndpoint + '/AzardBackend/model', model)
   //          	.then(
   //              	function(response) {
   //                  	console.log(response);
   //              	}
   //          	) 
   //  	}

        function getAllModels() {   
            return $http.get(Environment.apiEndpoint + '/AzardBackend/model')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }

        function submitModel(model) { 
            if (model.id) {
                return $http.post(Environment.apiEndpoint + '/AzardBackend/model', model)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            } else {
                return $http.put(Environment.apiEndpoint + '/AzardBackend/model', model)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            }
        }

	});