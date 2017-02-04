'use strict';

angular.module('kalafcheFrontendApp')
	.service('OrderedStockService', function($http, Environment) {
		angular.extend(this, {
            getAllOrderedStocks: getAllOrderedStocks,
			submitOrderedStock: submitOrderedStock,
            deleteOrderedStock: deleteOrderedStock
		});

    	function submitOrderedStock(orderedStock) {	
			return $http.put(Environment.apiEndpoint + '/KalafcheBackend/service/orderedStock', orderedStock)
            	.then(
                	function(response) {
                    	return response;
                	}
            	)
    	}

        function getAllOrderedStocks(stockOrderId) {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/orderedStock', {"params" : {"stockOrderId" : stockOrderId}})
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function deleteOrderedStock(orderedStockId, stockOrderId) {    
            return $http.delete(Environment.apiEndpoint + '/KalafcheBackend/service/orderedStock', {"params" : {"orderedStockId" : orderedStockId, "stockOrderId" : stockOrderId}})
                .then(
                    function(response) {
                        console.log(response);
                    }
                )
        }
	});