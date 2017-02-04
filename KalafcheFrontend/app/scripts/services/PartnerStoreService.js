'use strict';

angular.module('kalafcheFrontendApp')
	.service('PartnerStoreService', function($http, Environment) {
		angular.extend(this, {
			submitPartnerStore: submitPartnerStore,
            getAllPartnerStores: getAllPartnerStores
		});

    	function submitPartnerStore(partnerStore) {	
			return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/partnerStore/insertPartnerStore', partnerStore)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	)
    	}

        function getAllPartnerStores() {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/partnerStore/getAllPartnerStores')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }
	});