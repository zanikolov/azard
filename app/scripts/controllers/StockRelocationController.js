'use strict';

angular.module('kalafcheFrontendApp')
    .controller('StockRelocationController', function ($scope, StockRelocationService, SessionService, ApplicationService) {

        init();

        function init() {
            $scope.allStockRelocations = [];
            $scope.outgoingStockRelocations = [];
            $scope.incomingStockRelocations = [];

            $scope.selectedRelocationStatus = "pending";

            if ($scope.isAdmin()) {
                getAllStockRelocations();
            } else {
                getOutgoingStockRelocations();
                getIncomingStockRelocations();
            }

        };

        $scope.getRelocationTimestamp = function(relocationEpochTime) {

            if(relocationEpochTime != 0) {
                var timeStamp = new Date(relocationEpochTime);

                var minutes = ApplicationService.getTwoDigitNumber(timeStamp.getMinutes());
                var hh = ApplicationService.getTwoDigitNumber(timeStamp.getHours());
                var dd = ApplicationService.getTwoDigitNumber(timeStamp.getDate());
                var mm = ApplicationService.getTwoDigitNumber(timeStamp.getMonth() + 1); //January is 0!
                var yyyy = timeStamp.getFullYear();

                return dd + "-" + mm + "-" + yyyy + " " + hh + ":" + minutes;
            } else {
                return "";
            }
        };

        function getAllStockRelocations() {
            StockRelocationService.getAllStockRelocations().then(function(response) {
                $scope.allStockRelocations = response;
            });
        };

        function getOutgoingStockRelocations() {
            StockRelocationService.getOutgoingStockRelocations().then(function(response) {
                $scope.outgoingStockRelocations = response;
            });
        };

        function getIncomingStockRelocations() {
            StockRelocationService.getIncomingStockRelocations().then(function(response) {
                $scope.incomingStockRelocations = response;
            });
        };

        $scope.setStockRelocationArrived = function(relocation) {
            relocation.relocationCompleteTimestamp = ApplicationService.getCurrentTimestamp();
            StockRelocationService.setStockRelocationArrived(relocation).then(function(response) {
                relocation.arrived = true;
            });            
        }

        $scope.approveStockRelocation = function(relocation) {
            StockRelocationService.approveStockRelocation(relocation.id).then(function(response) {
                relocation.approved = true;
            });            
        }

        $scope.rejectStockRelocation = function(relocation) {
            relocation.relocationCompleteTimestamp = ApplicationService.getCurrentTimestamp();
            StockRelocationService.rejectStockRelocation(relocation).then(function(response) {
                relocation.rejected = true;
            });            
        }

        $scope.archiveStockRelocation = function(relocation) {
            StockRelocationService.archiveStockRelocation(relocation.id).then(function(response) {
                relocation.archived = true;
            });  
        }

        $scope.shouldArchiveButtonBeVisible = function(relocation) {

            if ($scope.isSuperAdmin() && (relocation.rejected || relocation.arrived)) {
                return true;
            }

            return false;
        }

        $scope.filterRelocation = function(relocation) {
            
            if (relocation.archived) {
                return false;
            }

            if ((!relocation.rejected || relocation.rejected === "undefined") && !relocation.arrived && ($scope.selectedRelocationStatus === "pending")) {
                return true;
            }

            if ((relocation.rejected || relocation.arrived) && ($scope.selectedRelocationStatus === "completed")) {
                return true;
            }

            return false;
        };
  });