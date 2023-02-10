'use strict';

angular.module('kalafcheFrontendApp')
	.service('ExpenseService', function($http, Environment) {
		angular.extend(this, {
			submitExpense: submitExpense,
			searchExpenses: searchExpenses,
            getExpenseTypes: getExpenseTypes
		});

        function submitExpense(expense, file) {
            var fileFormData = new FormData();
            fileFormData.append('expenseImage', file);
            fileFormData.append('price', expense.price);
            fileFormData.append('typeCode', expense.typeCode);
            fileFormData.append('description', expense.description);
            fileFormData.append('storeId', expense.storeId);

            return $http.post(Environment.apiEndpoint + '/AzardBackend/expense', fileFormData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
 
            }).then(function (response) {
                return response.data;
            })
        }

        function searchExpenses(startDateMilliseconds, endDateMilliseconds, storeIds) { 
            var params = {"params" : 
            	{"startDateMilliseconds": startDateMilliseconds, 
            	"endDateMilliseconds": endDateMilliseconds, 
                "storeIds": storeIds}};
            console.log(params);

            return $http.get(Environment.apiEndpoint + '/AzardBackend/expense', params)
                .then(
                    function(response) {
                        console.log(response.data);
                        return response.data
                    }
                );
        }


        function getExpenseTypes() {  
            return $http.get(Environment.apiEndpoint + '/AzardBackend/expense/type')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

	});