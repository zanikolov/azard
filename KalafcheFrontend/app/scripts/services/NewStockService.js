'use strict';

angular.module('kalafcheFrontendApp')
	.service('NewStockService', function($http, SessionService, Environment) {
		angular.extend(this, {
			submitNewStockForApproval: submitNewStockForApproval,
            getStocksWaitingForApproval: getStocksWaitingForApproval,
            approveNewStock: approveNewStock,
            getQuantityInStock: getQuantityInStock

		});

    	function submitNewStockForApproval(stockList) {	
			return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/stock/updateStocksForApproval', stockList)
            	.then(
                	function(response) {
                    	return response.data;
                	}
            	) 
    	}

        function getStocksWaitingForApproval() { 
            var kalafcheStoreId = SessionService.currentUser.employeeKalafcheStoreId;
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stock/getUnapprovedStocksByKalafcheStoreId', {"params" : {"kalafcheStoreId" : kalafcheStoreId}})
                .then(
                    function(response) {
                        return response.data;
                    }
                );
        }

        function approveNewStock(stockList) {  
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/stock/approveStocksForApproval', stockList)
                .then(
                    function(response) {
                        console.log(response);
                    }
                ) 
        }

        function getQuantityInStock(productId, deviceModelId) { 
            var kalafcheStoreId = SessionService.currentUser.employeeKalafcheStoreId;
            var params = {"params" : {"itemId": itemId, "kalafcheStoreId" : kalafcheStoreId}};
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stock/getQuantitiyOfStock', params)
                .then(
                    function(response) {
                        console.log(response.data)
                        return response.data;
                    }
                );
        }
	});