'use strict';

angular.module('kalafcheFrontendApp')
	.service('SaleService', function($http, Environment) {
		angular.extend(this, {
			submitSale: submitSale,
            getAllSales: getAllSales,
            searchSales: searchSales
		});

        function submitSale(sale) { 
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/sale/insertSale', sale)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function getAllSales() {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/sale/getAllSales')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function searchSales(startDateMilliseconds, endDateMilliseconds, kalafcheStoreId) { 
            if (!kalafcheStoreId) {
                kalafcheStoreId = 0;
            }
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds, "kalafcheStoreId": kalafcheStoreId}};
            console.log(params);

            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/sale/searchSales', params)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        }
	});