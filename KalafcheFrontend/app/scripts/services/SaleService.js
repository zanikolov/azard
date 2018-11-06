'use strict';

angular.module('kalafcheFrontendApp')
	.service('SaleService', function($http, Environment) {
		angular.extend(this, {
			submitSale: submitSale,
            getSaleItems: getSaleItems,
            searchSales: searchSales,
            searchSaleItems: searchSaleItems
		});

        function submitSale(sale) { 
            return $http.put(Environment.apiEndpoint + '/KalafcheBackend/sale', sale)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function getSaleItems(saleId) {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/sale/' + saleId)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function searchSales(startDateMilliseconds, endDateMilliseconds, kalafcheStoreIds, selectedBrandId, selectedModelId, productCode) { 
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds, 
                "kalafcheStoreIds": kalafcheStoreIds, "deviceBrandId": selectedBrandId, "deviceModelId": selectedModelId, "productCode": productCode}};
            console.log(params);

            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/sale', params)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        }

        function searchSaleItems(startDateMilliseconds, endDateMilliseconds, kalafcheStoreIds, selectedBrandId, selectedModelId, productCode) { 
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds, 
                "kalafcheStoreIds": kalafcheStoreIds, "deviceBrandId": selectedBrandId, "deviceModelId": selectedModelId, "productCode": productCode}};
            console.log(params);

            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/sale/saleItem', params)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        }

	});