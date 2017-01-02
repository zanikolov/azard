'use strict';

angular.module('kalafcheFrontendApp')
	.service('ItemService', function($http, Environment, ApplicationService) {
		angular.extend(this, {
			submitItem: submitItem,
            updateItem: updateItem,
            getAllItems: getAllItems,
            validateItem: validateItem
		});

    	function submitItem(item) {	
			return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/item/insertItem', item)
            	.then(
                	function(response) {
                    	return response.data;
                	}
            	) 
    	}

        function updateItem(item) { 
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/item/updateItem', item)
                .then(
                    function(response) {
                        console.log(response);
                    }
                ) 
        }

        function getAllItems() {   
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/item/getAllItems')
                .then(
                    function(response) {
                        return response.data;
                    }
                ) ;
        }

        function validateItem(item, items, itemForm) {
            if (itemForm.inputProductCode && !ApplicationService.validateItemCodeDuplication(item, items)) {
               itemForm.inputProductCode.$invalid = true;

                return false;

            } else if (!ApplicationService.validateDuplication(item.name, items)) {
                console.log(items);
                itemForm.inputName.$invalid = true;

                return false;

            } else if (!itemForm.$valid) {
                if (itemForm.inputProductCode) {
                    itemForm.inputProductCode.$dirty = true;
                }
                itemForm.inputName.$dirty = true;
                itemForm.inputPrice.$dirty = true;
                itemForm.inputPurchasePrice.$dirty = true;

                return false;

            } else  {
                return true;
            }
        };

	});