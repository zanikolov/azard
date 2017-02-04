'use strict';

angular.module('kalafcheFrontendApp')
	.service('ColorService', function($http, Environment) {
		angular.extend(this, {
            getAllColors: getAllColors
		});

        function getAllColors() {   
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/color/getAllColors')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});

