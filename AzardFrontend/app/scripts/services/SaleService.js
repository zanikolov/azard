'use strict';

angular.module('kalafcheFrontendApp')
	.service('SaleService', function($http, Environment, FileSaver) {
		angular.extend(this, {
			submitSale: submitSale,
            getSaleItems: getSaleItems,
            getSalesByStores: getSalesByStores,
            searchSales: searchSales,
            searchSaleItems: searchSaleItems,
            getTotalSum: getTotalSum,
            generateExcel: generateExcel,
            getMonthlyTurnover: getMonthlyTurnover
		});

        function getTotalSum(items, code) {
            var prices = [];
            angular.forEach(items, function(item) {
                prices.push(item.price);
            })

            var request = {};
            request.prices = prices;
            request.discountCode = code

            return $http.post(Environment.apiEndpoint + '/AzardBackend/sale/totalSum', request)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function submitSale(sale) { 
            return $http.put(Environment.apiEndpoint + '/AzardBackend/sale', sale)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function getSaleItems(saleId) {  
            return $http.get(Environment.apiEndpoint + '/AzardBackend/sale/' + saleId)
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

            return $http.get(Environment.apiEndpoint + '/AzardBackend/sale', params)
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

            return $http.get(Environment.apiEndpoint + '/AzardBackend/sale/saleItem', params)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function getSalesByStores(startDateMilliseconds, endDateMilliseconds, selectedBrandId, selectedModelId, productCode, productTypeId) { 
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds,
             "deviceBrandId": selectedBrandId, "deviceModelId": selectedModelId, "productCode": productCode, "productTypeId": productTypeId}};

            return $http.get(Environment.apiEndpoint + '/AzardBackend/sale/store', params)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function generateExcel(saleItems, startDateMilliseconds, endDateMilliseconds) { 
            var request = {};
            request.saleItems = saleItems;
            request.startDate = startDateMilliseconds;
            request.endDate = endDateMilliseconds;

            return $http.post(Environment.apiEndpoint + '/AzardBackend/sale/excel', request, {responseType: "arraybuffer"})
                .then(
                    function(response) {
                        var blob = new Blob([response.data], {type: "application/vnd.openxmlformat-officedocument.spreadsheetml.sheet;"});
                        FileSaver.saveAs(blob, 'Справка продажби артикули.xlsx')
                    }
                );
        }

        function getMonthlyTurnover(month) {
            var params = {"params" : {"month": month}};

            return $http.get(Environment.apiEndpoint + '/AzardBackend/sale/pastPeriods', params)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

	});