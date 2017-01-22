'use strict';

angular.module('kalafcheFrontendApp')
    .controller('EmployeeController', function($scope, EmployeeService, KalafcheStoreService, ApplicationService) {
        
       init();

        function init() {
            $scope.newEmployee = {};
            $scope.kalafcheStores = [];
            $scope.employees = [];
            $scope.isErrorMessageVisible = false;  
            $scope.errorMessage = ""; 
            $scope.currentPage = 1; 
            $scope.employeesPerPage = 10; 
            $scope.selectedKalafcheStore = {};

            getAllKalafcheStores();
            getAllEmployees();
        }

        $scope.submitEmployee = function() {
            if (ApplicationService.validateDuplication($scope.newEmployee.name, $scope.employees)) {           
                EmployeeService.submitEmployee($scope.newEmployee).then(function(response) {
                    $scope.newEmployee.enabled = true;
                    $scope.newEmployee.kalafcheStoreName = getEmployeeKalafcheStoreName($scope.newEmployee.kalafcheStoreId);
                    $scope.employees.push($scope.newEmployee);
                    resetEmployeeState();
                    $scope.isErrorMessageVisible = false; 
                    $scope.employeeForm.$setPristine();
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Има въведен служител с това име";
            }
        };

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        $scope.deactivateAccount = function(emlpoyee) {
            EmployeeService.deactivateAccount(employee.userId).then(function(response) {
                employee.enabled = false;
            });
        };

        function getAllKalafcheStores() {
            KalafcheStoreService.getAllKalafcheStores().then(function(response) {
                $scope.kalafcheStores = response;
                console.log($scope.kalafcheStores);
            });

        };

        function getAllEmployees() {
            EmployeeService.getAllEmployees().then(function(response) {
                $scope.employees = response;
            });

        };

        function resetEmployeeState() {
            $scope.newEmployee = {};
        }

        function getEmployeeKalafcheStoreName(kalafcheStoreId) {
            var stores = $scope.kalafcheStores;
            for (var i = 0; i < stores.length; i++) {
                if (stores[i].id === kalafcheStoreId) {
                    return stores[i].city + ', ' + stores[i].name;
                }
            }
        }
    });