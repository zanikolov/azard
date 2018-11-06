'use strict';

angular.module('kalafcheFrontendApp')
	.service('NewStockService', function($http, SessionService, Environment, $q) {
		angular.extend(this, {
			submitNewStock: submitNewStock,
            deleteNewStock: deleteNewStock,
            getAllNewStocks: getAllNewStocks,
            validateFile: validateFile,
            importFile: importFile,
            approveNewStock: approveNewStock,
            approveAllNewStocks: approveAllNewStocks,
            printStickersForNewStocks: printStickersForNewStocks
		});

    	function submitNewStock(newStock) {	
            var params = {"params" : {"productId": newStock.productId, "deviceModelId": newStock.deviceModelId, "quantity": newStock.quantity}};
            
			return $http.put(Environment.apiEndpoint + '/KalafcheBackend/newStock', newStock)
            	.then(
                	function(response) {
                    	return response.data;
                	}
            	) 
    	}

        function getAllNewStocks() {
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/newStock')
                .then(
                    function(response) {
                        return response.data;
                    }
                ); 
        }

        function deleteNewStock(newStockId) {
            return $http.delete(Environment.apiEndpoint + '/KalafcheBackend/newStock', {"params": {"newStockId": newStockId}})
                .then(
                    function(response) {
                        return response.data;
                    }
                ); 
        }

        function approveNewStock(newStock) {
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/newStock/approve', newStock)
                .then(
                    function(response) {
                        return response.data;
                    }
                ); 
        }

        function approveAllNewStocks(newStocks) {
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/newStock/approveAll', newStocks)
                .then(
                    function(response) {
                        return response.data;
                    }
                ); 
        }

        function printStickersForNewStocks(newStocks) {
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/newStock/printAll', newStocks)
                .then(
                    function(response) {
                        return response.data;
                    }
                ); 
        }

        function validateFile(file) {
            var fileFormData = new FormData();
            fileFormData.append('newStockFile', file);

            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/newStock/validate', fileFormData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
 
            }).then(function (response) {
                return response.data;
            })
        }

        function importFile(file) {
            var uploadUrl = Environment.apiEndpoint + '/KalafcheBackend/newStock/import';
            var fileFormData = new FormData();
            fileFormData.append('newStockFile', file);

            return $http.post(uploadUrl, fileFormData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
 
            }).then(function (response) {
                return response.data;
            })
        }

	});