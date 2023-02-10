'use strict';

angular.module('kalafcheFrontendApp')
	.service('StockOrderService', function($http, Environment) {
		angular.extend(this, {
            getCurrentStockOrder: getCurrentStockOrder
		});

    	function getCurrentStockOrder() {	
			return $http.get(Environment.apiEndpoint + '/AzardBackend/service/stockOrder')
            	.then(
                	function(response) {
                    	return response;
                	}
            	)
    	}
	});