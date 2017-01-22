'use strict';

angular.module('kalafcheFrontendApp')
	.service('EmployeeService', function($http, Environment) {
		angular.extend(this, {
			getAllEmployees: getAllEmployees,
            submitEmployee: submitEmployee
		});

        function submitEmployee(employee) {   
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/employee', employee)
                .then(
                    function(response) {
                        console.log(response);
                    }
                )
        }

        function getAllEmployees() {  
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/employee/all')
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }

        function deactivateAccount(userId) { 
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/employee', {"params" : {"userId" : userId}})
                .then(
                    function(response) {
                        console.log(response);
                    }
                );
        }

	});