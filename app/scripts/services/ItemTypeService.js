'use strict';

angular.module('kalafcheFrontendApp')
	.service('ItemTypeService', function($http) {
		angular.extend(this, {
			submitItemType: submitItemType,
            getAllItemTypes: getAllItemTypes
		});

    	function submitItemType(type) {	
			return $http.post('http://localhost:8080/KalafcheBackend/service/itemType/insertItemType', type)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	) 
    	}

        function getAllItemTypes() {   
            return $http.get('http://localhost:8080/KalafcheBackend/service/itemType/getAllItemTypes')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});