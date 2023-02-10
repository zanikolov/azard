'use strict';

angular.module('kalafcheFrontendApp')
	.service('PartnerStoreService', function($http, Environment) {
		angular.extend(this, {
			submitPartnerStore: submitPartnerStore,
            getAllPartnerStores: getAllPartnerStores
		});

        function getAllPartnerStores() {   
            return $http.get(Environment.apiEndpoint + '/AzardBackend/partnerStore')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        };

        function submitPartnerStore(partnerStore) { 
            if (partnerStore.id) {
                return $http.post(Environment.apiEndpoint + '/AzardBackend/partnerStore', partnerStore)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            } else {
                return $http.put(Environment.apiEndpoint + '/AzardBackend/partnerStore', partnerStore)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            }
        };
	});