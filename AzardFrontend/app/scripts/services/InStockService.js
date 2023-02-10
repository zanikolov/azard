'use strict';

angular.module('kalafcheFrontendApp')
	.service('InStockService', function($http, Environment, FileSaver) {
		angular.extend(this, {
            getInStock: getInStock,
            getAllInStockForReport: getAllInStockForReport,
            printStickersForStocks: printStickersForStocks

		});

    function getInStock(userStoreId, selectedStoreId, brandId, modelId, barcode) {
        return $http.get(Environment.apiEndpoint + '/AzardBackend/stock', 
          {"params" : {
            "userStoreId" : userStoreId, 
            "selectedStoreId" : selectedStoreId,
            "brandId" : brandId,
            "modelId" : modelId,
            "barcode" : barcode}})
            .then(
                function(response) {
                    return response.data
                }
            );
    }

    function getAllInStockForReport() { 
        return $http.get(Environment.apiEndpoint + '/AzardBackend/stock/getAllStocksForReport')
            .then(
                function(response) {
                    return response.data
                }
            );
    }

    function printStickersForStocks(storeId) {
      return $http.get(Environment.apiEndpoint + '/AzardBackend/stock/printStickers/' + storeId, {responseType: "blob"})
          .then(
              function(response) {
                console.log(response.data);
                  var blob = new Blob([response.data], {type: "application/pdf"});
                  FileSaver.saveAs(blob, 'Етикети за нова стока.pdf')
              }
          ); 
    }
  
	});