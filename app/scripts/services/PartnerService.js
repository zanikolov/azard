'use strict';

angular.module('kalafcheFrontendApp')
	.service('PartnerService', function($http, Environment) {
		angular.extend(this, {
			getPartnerByCode: getPartnerByCode,
            submitPartner: submitPartner,
            getAllPartners: getAllPartners
		});

        function getPartnerByCode(partnerCode) { 
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/partner/getPartnerByCode', {"params" : {"partnerCode" : partnerCode}})
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function submitPartner(partner) {   
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/partner/insertPartner', partner)
                .then(
                    function(response) {
                        console.log(response);
                    }
                )
        }

        function getAllPartners() {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/partner/getAllPartners')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

	});