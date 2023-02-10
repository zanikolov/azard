'use strict';

angular.module('kalafcheFrontendApp')
    .directive('expenseReport', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/expense/report.html',
            controller: ExpenseReportController
        }
    });

    function ExpenseReportController($scope, $rootScope, $mdDialog, ApplicationService, ExpenseService, AuthService, StoreService, SessionService) {

        init();

        function init() {
            $scope.currentPage = 1;  
            $scope.expensesPerPage = 15;
            $scope.expenses = []; 
            $scope.stores = [];
            $scope.selectedStore = {};
            
            $scope.dateFormat = 'dd-MMMM-yyyy';
            $scope.startDate = {};
            $scope.endDate = {};
            $scope.startDateMilliseconds = {};
            $scope.endDateMilliseconds = {};
            $scope.startDatePopup = {opened: false};
            $scope.endDatePopup = {opened: false};

            getCurrentDate();
            $scope.startDateOptions = {
                formatYear: 'yy',
                maxDate: new Date(),
                minDate: new Date(2015, 5, 22),
                startingDay: 1,
                showWeeks: false
            };
            $scope.endDateOptions = {
                formatYear: 'yy',
                maxDate: new Date(2025, 5, 22),
                minDate: $scope.startDate,
                startingDay: 1,
                showWeeks: false
            };

            getAllStores(); 
        }

        function getCurrentDate() {
            $scope.startDate = new Date();
            $scope.startDate.setHours(0);
            $scope.startDate.setMinutes(0);
            $scope.startDateMilliseconds = $scope.startDate.getTime();
            $scope.endDate = new Date();
            $scope.endDate.setHours(23);
            $scope.endDate.setMinutes(59);
            $scope.endDateMilliseconds = $scope.endDate.getTime();

        };

        $scope.searchExpenses = function() {
            ExpenseService.searchExpenses($scope.startDateMilliseconds, $scope.endDateMilliseconds, $scope.selectedStore.id).then(function(response) {
                $scope.expenses = response.expenses;
            });    
        }

        $scope.clear = function() {
            $scope.startDate = null;
            $scope.endDate = null;
        };

        $scope.changeStartDate = function() {
            $scope.endDateOptions.minDate = $scope.startDate;
            $scope.startDate.setHours(0);
            $scope.startDate.setMinutes(1);
            $scope.startDateMilliseconds = $scope.startDate.getTime();
        };

        $scope.changeEndDate = function() {
            $scope.endDate.setHours(23);
            $scope.endDate.setMinutes(59);
            $scope.endDateMilliseconds = $scope.endDate.getTime();
        };

        $scope.changeStore = function() {
            $scope.searchSales();
        };

        function getAllStores() {
            StoreService.getAllStores().then(function(response) {
                $scope.stores = response;
                $scope.selectedStore =  {"id": SessionService.currentUser.employeeStoreId};
                $scope.searchExpenses();
            });

        };

        $scope.getTimestamp = function(timestamp) {
            return ApplicationService.convertEpochToTimestamp(timestamp);
        };

        $scope.resetCurrentPage = function() {
            $scope.currentPage = 1;
        };

        $scope.expand = function(sale) {
            sale.expanded = !sale.expanded;
        };

        $scope.isAdmin = function() {
            return AuthService.isAdmin();
        };

        $scope.showImage = function(expense){
            $mdDialog.show({
                locals:{imgSrc:"https://drive.google.com/uc?export=view&id=" + expense.fileId},
                controller: function($scope, imgSrc) { $scope.imgSrc = imgSrc; },
                templateUrl: 'views/modals/image-modal.html',
                clickOutsideToClose:true,
                parent: angular.element(document.body)
            })
            .then(function(answer) {
                $scope.status = 'You said the information was "".';
            }, function() {
                $scope.status = 'You cancelled the dialog.';
            });
        };

        $rootScope.$on("ExpenseCreated", function () {
            console.log("<><><><>");
            $scope.searchExpenses();
        })

    };

