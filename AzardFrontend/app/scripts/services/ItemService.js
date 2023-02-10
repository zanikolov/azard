'use strict';

angular.module('kalafcheFrontendApp')
	.service('ItemService', function($http, Environment, ApplicationService) {
		angular.extend(this, {
			submitItem: submitItem,
            updateItem: updateItem,
            getAllItems: getAllItems,
            insertItemOrUpdateBarcode: insertItemOrUpdateBarcode,
            getItemSpecificPrice: getItemSpecificPrice,
            checkIfItemExists: checkIfItemExists
		});

    	function submitItem(item) {	
            if (item.id) {
                return $http.post(Environment.apiEndpoint + '/AzardBackend/item', item)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            } else {
    			return $http.put(Environment.apiEndpoint + '/AzardBackend/item/upsert', item)
                	.then(
                    	function(response) {
                        	return response.data;
                    	}
                	)
            }
    	}

        function updateItem(item) { 
            return $http.post(Environment.apiEndpoint + '/AzardBackend/item', item)
                .then(
                    function(response) {
                        console.log(response);
                    }
                ) 
        }

        function getAllItems() {   
            return $http.get(Environment.apiEndpoint + '/AzardBackend/item')
                .then(
                    function(response) {
                        return response.data;
                    }
                ) ;
        }


        function checkIfItemExists(item) {   
            return $http.post(Environment.apiEndpoint + '/AzardBackend/item/exists', item)
                .then(
                    function(response) {
                        return response.data;
                    }
                ) ;
        }

        function insertItemOrUpdateBarcode(item) { 
            return $http.put(Environment.apiEndpoint + '/AzardBackend/item/upsert', item)
                .then(
                    function(response) {
                        console.log(response);
                    }
                ) 
        }

        function getItemSpecificPrice(itemId) {   
            return $http.get(Environment.apiEndpoint + '/AzardBackend/item/specificPrice/' + itemId)
                .then(
                    function(response) {
                        return response.data;
                    }
                ) ;
        }

	});