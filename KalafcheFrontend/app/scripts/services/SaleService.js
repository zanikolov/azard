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

        function searchSales(startDateMilliseconds, endDateMilliseconds, kalafcheStoreIds, selectedBrandId, selectedModelId, productCode) { 
            // if (!kalafcheStoreId) {
            //     kalafcheStoreId = 0;
            // }
            console.log(">>>>> 1 " + kalafcheStoreIds);
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds, 
                "kalafcheStoreIds": kalafcheStoreIds, "deviceBrandId": selectedBrandId, "deviceModelId": selectedModelId, "itemProductCode": productCode}};
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