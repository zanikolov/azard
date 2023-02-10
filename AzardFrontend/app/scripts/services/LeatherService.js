'use strict';

angular.module('kalafcheFrontendApp')
	.service('LeatherService', function($http, Environment) {
		angular.extend(this, {
			submitLeather: submitLeather,
            getAllLeathers: getAllLeathers
		});

        function submitLeather(leather) { 
            if (leather.id) {
                return $http.post(Environment.apiEndpoint + '/AzardBackend/leather', leather)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            } else {
                return $http.put(Environment.apiEndpoint + '/AzardBackend/leather', leather)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            }
        }

        function getAllLeathers() {
            return $http.get(Environment.apiEndpoint + '/AzardBackend/leather')
                .then(
                    function(response) {
                        return response.data;
                    }
                ) ;
        }

	});