'use strict';

angular.module('kalafcheFrontendApp')
	.service('PartnerService', function($http, Environment) {
		angular.extend(this, {
			getPartnerByCode: getPartnerByCode,
            submitPartner: submitPartner,
            getAllPartners: getAllPartners
		});

        function getPartnerByCode(discountCode) { 
            return $http.get(Environment.apiEndpoint + '/AzardBackend/partner/' + discountCode)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function submitPartner(partner) { 
            if (partner.id) {
                return $http.post(Environment.apiEndpoint + '/AzardBackend/partner', partner)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            } else {
                return $http.put(Environment.apiEndpoint + '/AzardBackend/partner', partner)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            }
        }

        function getAllPartners() {  
            return $http.get(Environment.apiEndpoint + '/AzardBackend/partner')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

	});