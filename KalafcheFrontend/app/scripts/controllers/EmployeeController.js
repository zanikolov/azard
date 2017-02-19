'use strict';

angular.module('kalafcheFrontendApp')
    .controller('EmployeeController', function($scope, EmployeeService, KalafcheStoreService, ApplicationService) {
        
        var addNewEmployeeTitle = "Въвеждане на нов служител";
        var editEmployeeTitle = "Редактиране на служител";
        
        init();

        function init() {
            $scope.pageTitle = addNewEmployeeTitle;
            $scope.newEmployee = {};
            $scope.selectedEmployee = null;
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
                    console.log($scope.employeeForm);
                    $scope.employeeForm.$setPristine();
                    resetEmployeeState();
                    $scope.isErrorMessageVisible = false; 
                    
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Има въведен служител с това име";
            }
        };

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        $scope.deactivateAccount = function(employee) {
            EmployeeService.deactivateAccount(employee.userId).then(function(response) {
                employee.enabled = false;
            });
        };

        $scope.selectEmployeeForEdit = function(employee) {
            $scope.selectedEmployee = angular.copy(employee);
            $scope.pageTitle = editEmployeeTitle;
        };

        $scope.cancelEditEmployee = function() {
            $scope.selectedEmployee = null;
            $scope.pageTitle = addNewEmployeeTitle;
            $scope.resetErrorMessage();
        };

        $scope.editEmployee = function() {
            if (ApplicationService.validateDuplication($scope.selectedEmployee.name, $scope.employees)) {    
                EmployeeService.updateEmployee($scope.selectedEmployee).then(function(response) {
                    $scope.cancelEditEmployee();
                    getAllEmployees();
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Има въведен служител с това име";
            }
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