'use strict';

angular.module('kalafcheFrontendApp')
	.service('SaleService', function($http, Environment, FileSaver) {
		angular.extend(this, {
			submitSale: submitSale,
            getSaleItems: getSaleItems,
            searchSales: searchSales,
            searchSaleItems: searchSaleItems,
            getTotalSum: getTotalSum,
            generateExcel: generateExcel
		});

        function getTotalSum(items, discount) {
            var prices = [];
            angular.forEach(items, function(item) {
                prices.push(item.productPrice);
            })

            var request = {};
            request.prices = prices;
            request.discount = discount

            console.log(request);

            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/sale/totalSum', request)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        };

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

        function searchSales(startDateMilliseconds, endDateMilliseconds, storeIds, selectedBrandId, selectedModelId, productCode) { 
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds, 
                "storeIds": storeIds, "deviceBrandId": selectedBrandId, "deviceModelId": selectedModelId, "productCode": productCode}};
            console.log(params);

            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/sale', params)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        }

        function searchSaleItems(startDateMilliseconds, endDateMilliseconds, storeIds, selectedBrandId, selectedModelId, productCode, productTypeId) { 
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds, 
                "storeIds": storeIds, "deviceBrandId": selectedBrandId, "deviceModelId": selectedModelId, "productCode": productCode, "productTypeId": productTypeId}};
            console.log(params);

            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/sale/saleItem', params)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        }

        function generateExcel(saleItems, startDateMilliseconds, endDateMilliseconds) { 
            var request = {};
            request.saleItems = saleItems;
            request.startDate = startDateMilliseconds;
            request.endDate = endDateMilliseconds;

            console.log(request);

            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/sale/excel', request, {responseType: "arraybuffer"})
                .then(
                    function(response) {
                        var blob = new Blob([response.data], {type: "application/vnd.openxmlformat-officedocument.spreadsheetml.sheet;"});
                        FileSaver.saveAs(blob, 'Справка продажби артикули.xlsx')
                    }
                );
        }

	});