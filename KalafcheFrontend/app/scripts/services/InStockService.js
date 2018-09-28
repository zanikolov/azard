'use strict';

angular.module('kalafcheFrontendApp')
	.service('InStockService', function($http, Environment) {
		angular.extend(this, {
            getAllInStock: getAllInStock,
            getAllInStockForReport: getAllInStockForReport

		});

   //  	function submitNewStockForApproval(stockList) {	
			// return $http.post('http://localhost:8080/KalafcheBackend/service/item/updateItemsForApproval', stockList)
   //          	.then(
   //              	function(response) {
   //                  	console.log(response);
   //              	}
   //          	) 
   //  	}

  function getAllInStock(userKalafcheStoreId, selectedKalafcheStoreId) {
      console.log(">>>>>>> " + userKalafcheStoreId + "  " + selectedKalafcheStoreId);
      return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stock/getAllApprovedStocks', {"params" : {"userKalafcheStoreId" : userKalafcheStoreId, "selectedKalafcheStoreId" : selectedKalafcheStoreId}})
          .then(
              function(response) {
                  return response.data
              }
          );
  }

  function getAllInStockForReport() { 
      return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stock/getAllStocksForReport')
          .then(
              function(response) {
                  return response.data
              }
          );
  }
  
	});