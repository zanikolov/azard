'use strict';

angular.module('kalafcheFrontendApp')
	.service('ColorService', function($http) {
		angular.extend(this, {
            getAllColors: getAllColors
		});

        function getAllColors() {   
            return $http.get('http://localhost:8080/KalafcheBackend/service/color/getAllColors')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});