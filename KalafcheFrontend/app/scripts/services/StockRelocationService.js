'use strict';

angular.module('kalafcheFrontendApp')
	.service('StockRelocationService', function($http, Environment, SessionService) {
		angular.extend(this, {
			submitRelocation: submitRelocation,
            getAllStockRelocations: getAllStockRelocations,
            getOutgoingStockRelocations: getOutgoingStockRelocations,
            getIncomingStockRelocations: getIncomingStockRelocations,
            setStockRelocationArrived: setStockRelocationArrived,
            approveStockRelocation: approveStockRelocation,
            rejectStockRelocation: rejectStockRelocation,
            archiveStockRelocation: archiveStockRelocation
		});

        function submitRelocation(stock) { 
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/insertStockRelocation', stock)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function getAllStockRelocations() { 
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/getAllStockRelocations')
                .then(
                    function(response) {
                        return response.data;
                    }
                );
        }; 

        function getIncomingStockRelocations() { 
            var kalafcheStoreId = SessionService.currentUser.employeeKalafcheStoreId;
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/getIncomingStockRelocations', {"params" : {"kalafcheStoreId" : kalafcheStoreId}})
                .then(
                    function(response) {
                        return response.data;
                    }
                );
        };  

        function getOutgoingStockRelocations() { 
            var kalafcheStoreId = SessionService.currentUser.employeeKalafcheStoreId;
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/getOutgoingStockRelocations', {"params" : {"kalafcheStoreId" : kalafcheStoreId}})
                .then(
                    function(response) {
                        return response.data;
                    }
                );
        }; 

        function setStockRelocationArrived(relocation) {
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/setStockRelocationArrived', relocation)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function approveStockRelocation(relocationId) {;
            console.log(relocationId);
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/approveStockRelocation', relocationId)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function rejectStockRelocation(relocation) {
            var relocationId = relocation.id;
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/rejectStockRelocation', relocation)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function archiveStockRelocation(relocationId) {;
            console.log(relocationId);
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/stockRelocation/archiveStockRelocation', relocationId)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };
	});