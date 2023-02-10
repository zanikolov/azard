'use strict';

angular.module('kalafcheFrontendApp')
	.service('RelocationService', function($http, Environment, SessionService) {
		angular.extend(this, {
			submitRelocation: submitRelocation,
            changeRelocationStatus: changeRelocationStatus,
            archiveRelocation: archiveRelocation,
            searchRelocations: searchRelocations
		});

        function submitRelocation(relocation) { 
            return $http.put(Environment.apiEndpoint + '/AzardBackend/relocation', relocation)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function searchRelocations(sourceStoreId, destStoreId, isCompleted) { 
            return $http.get(Environment.apiEndpoint + 
                '/AzardBackend/relocation', {"params" : {"sourceStoreId" : sourceStoreId, "destStoreId": destStoreId, "isCompleted": isCompleted}})
                .then(
                    function(response) {
                        return response.data;
                    }
                );
        }; 

        function changeRelocationStatus(relocation) {
            return $http.post(Environment.apiEndpoint + '/AzardBackend/relocation', relocation)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function archiveRelocation(relocationId) {;
            return $http.post(Environment.apiEndpoint + '/AzardBackend/relocation/archive', relocationId)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };
	});