'use strict';

angular.module('kalafcheFrontendApp')
	.service('InStockService', function($http, Environment) {
		angular.extend(this, {
            getAllInStock: getAllInStock,
            getAllInStockForReport: getAllInStockForReport

		});

  function getAllInStock(userKalafcheStoreId, selectedKalafcheStoreId) {
      return $http.get(Environment.apiEndpoint + '/KalafcheBackend/stock', {"params" : {"userStoreId" : userKalafcheStoreId, "selectedStoreId" : selectedKalafcheStoreId}})
          .then(
              function(response) {
                  return response.data
              }
          );
  }

  function getAllInStockForReport() { 
      return $http.get(Environment.apiEndpoint + '/KalafcheBackend/stock/getAllStocksForReport')
          .then(
              function(response) {
                  return response.data
              }
          );
  }
  
	});