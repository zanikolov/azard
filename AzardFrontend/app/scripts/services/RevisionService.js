'use strict';

angular.module('kalafcheFrontendApp')
	.service('RevisionService', function($http, Environment, SessionService) {
		angular.extend(this, {
			initiateRevision: initiateRevision,
            getCurrentRevision: getCurrentRevision,
            getRevisionTypes: getRevisionTypes,
            getRevisionItemByBarcode: getRevisionItemByBarcode,
            findRevisionItem: findRevisionItem,
            submitRevision: submitRevision,
            searchRevisions: searchRevisions,
            getRevisionDetailedData: getRevisionDetailedData
		});

        function initiateRevision(revision) { 
            return $http.put(Environment.apiEndpoint + '/AzardBackend/revision', revision)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function getCurrentRevision(storeId) {
            var params = {"params" : {"storeId": storeId}};
            console.log(">>>>>>> " + storeId);
            return $http.get(Environment.apiEndpoint + '/AzardBackend/revision/current', params)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function getRevisionTypes() {
            return $http.get(Environment.apiEndpoint + '/AzardBackend/revision/type')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function getRevisionItemByBarcode(revisionId, barcode) {
            return $http.get(Environment.apiEndpoint + '/AzardBackend/revision/' + revisionId + '/item/' + barcode)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function findRevisionItem(revisionItem) { 
            return $http.post(Environment.apiEndpoint + '/AzardBackend/revision/item', revisionItem)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function syncRevisionItemQuantity(revisionItem) { 
            return $http.post(Environment.apiEndpoint + '/AzardBackend/revision/sync', revisionItem)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function submitRevision(revision) { 
            return $http.post(Environment.apiEndpoint + '/AzardBackend/revision', revision)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        };

        function getRevisionDetailedData(revisionId) {  
            return $http.get(Environment.apiEndpoint + '/AzardBackend/revision/' + revisionId)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function searchRevisions(startDateMilliseconds, endDateMilliseconds, storeId) { 
            var params = {"params" : {"startDateMilliseconds": startDateMilliseconds, "endDateMilliseconds": endDateMilliseconds, 
                "storeId": storeId}};

            return $http.get(Environment.apiEndpoint + '/AzardBackend/revision', params)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        }

	});