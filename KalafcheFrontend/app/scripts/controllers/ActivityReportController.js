'use strict';

angular.module('kalafcheFrontendApp')
    .controller('ActivityReportController', function($scope, ApplicationService, ActivityReportService, EmployeeService) {

        init();

        function init() {
            $scope.currentPage = 1;  
            $scope.activitiesPerPage = 15;
            $scope.activities = []; 
            $scope.employees = [];
            $scope.selectedEmployee = {};
            
            $scope.dateFormat = 'dd-MMMM-yyyy';
            $scope.activityDate = {};
            $scope.activityDateMilliseconds = {};
            // $scope.endDateMilliseconds = {};
            $scope.activityDatePopup = {opened: false};

            getCurrentDate();
            $scope.activityDateOptions = {
                formatYear: 'yy',
                maxDate: new Date(),
                minDate: new Date(2015, 5, 22),
                startingDay: 1,
                showWeeks: false
            };

            getAllEmployees();   
        }

        function getCurrentDate() {
            $scope.activityDate = new Date();
            $scope.activityDate.setHours(0);
            $scope.activityDate.setMinutes(0);
            $scope.activityDateMilliseconds = $scope.activityDate.getTime();
        };

        $scope.changeActivityDate = function() {
            console.log(">>>>><<<<<");
            //$scope.endDateOptions.minDate = $scope.startDate;
            $scope.activityDate.setHours(0);
            $scope.activityDate.setMinutes(1);
            $scope.activityDateMilliseconds = $scope.activityDate.getTime();

            $scope.searchActivities();
        };

        $scope.searchActivities = function() {
           getActivities();         
        }

        function getActivities() {
            console.log($scope.selectedEmployee.id);
            ActivityReportService.searchActivities($scope.activityDateMilliseconds).then(function(response) {
                $scope.activities = response;
                console.log($scope.activities);
            });           
        }

        // $scope.clear = function() {
        //     $scope.startDate = null;
        //     $scope.endDate = null;
        // };

        $scope.changeEmployee = function() {
            $scope.searchSales();
        };

        $scope.openActivityDatePopup = function() {
            $scope.activityDatePopup.opened = true;
        };

        function getAllEmployees() {
            EmployeeService.getAllActiveEmployees().then(function(response) {
                $scope.employees = response;
                //$scope.selectedKalafcheStore = KalafcheStoreService.getRealSelectedStore($scope.kalafcheStores, $scope.isAdmin());
                getActivities();
            });

        };

        $scope.getActivityTimestamp = function(activityTimestamp) {
            var timeStamp = new Date(activityTimestamp);
            console.log(">>>>> " + timeStamp.getTimezoneOffset());

            var minutes = ApplicationService.getTwoDigitNumber(timeStamp.getMinutes());
            var hh = ApplicationService.getTwoDigitNumber(timeStamp.getHours()) + timeStamp.getTimezoneOffset();
            var dd = ApplicationService.getTwoDigitNumber(timeStamp.getDate());
            var mm = ApplicationService.getTwoDigitNumber(timeStamp.getMonth() + 1); //January is 0!
            var yyyy = timeStamp.getFullYear();

            return dd + "-" + mm + "-" + yyyy + " " + hh + ":" + minutes;
        };

        $scope.resetCurrentPage = function() {
            $scope.currentPage = 1;
        };
    });