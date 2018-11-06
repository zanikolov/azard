'use strict';

angular.module('kalafcheFrontendApp')
	.service('PartnerService', function($http, Environment) {
		angular.extend(this, {
			getPartnerByCode: getPartnerByCode,
            submitPartner: submitPartner,
            getAllPartners: getAllPartners
		});

        function getPartnerByCode(partnerCode) { 
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/partner/' + partnerCode)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function submitPartner(partner) {   
            return $http.put(Environment.apiEndpoint + '/KalafcheBackend/partner', partner)
                .then(
                    function(response) {
                        console.log(response);
                    }
                )
        }

        function getAllPartners() {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/partner')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

	});