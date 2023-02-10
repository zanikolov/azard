'use strict';

angular.module('kalafcheFrontendApp')
	.service('LoyalCustomerService', function($http, Environment) {
		angular.extend(this, {
			getLoyalCustomerByCode: getLoyalCustomerByCode,
            submitLoyalCustomer: submitLoyalCustomer,
            getAllLoyalCustomers: getAllLoyalCustomers
		});

        function getLoyalCustomerByCode(discountCode) { 
            return $http.get(Environment.apiEndpoint + '/AzardBackend/loyalCustomer/' + discountCode)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function submitLoyalCustomer(loyalCustomer) { 
            if (loyalCustomer.id) {
                return $http.post(Environment.apiEndpoint + '/AzardBackend/loyalCustomer', loyalCustomer)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            } else {
                return $http.put(Environment.apiEndpoint + '/AzardBackend/loyalCustomer', loyalCustomer)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            }
        }

        function getAllLoyalCustomers() {  
            return $http.get(Environment.apiEndpoint + '/AzardBackend/loyalCustomer')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

	});