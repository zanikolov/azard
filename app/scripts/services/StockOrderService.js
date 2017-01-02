'use strict';

angular.module('kalafcheFrontendApp')
	.service('StockOrderService', function($http, Environment) {
		angular.extend(this, {
            getCurrentStockOrder: getCurrentStockOrder
		});

    	function getCurrentStockOrder() {	
			return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stockOrder')
            	.then(
                	function(response) {
                    	return response;
                	}
            	)
    	}
	});