'use strict';

angular.module('kalafcheFrontendApp')
    .directive('saleReportByTransaction', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/sale-report/by-transaction.html',
            controller: SaleTransactionReportController
        }
    });

    function SaleTransactionReportController($scope, $rootScope, ApplicationService, SaleService, AuthService,  KalafcheStoreService, BrandService, ModelService) {

        init();

        function init() {
            $scope.currentPage = 1;  
            $scope.salesPerPage = 15;
            $scope.sales = []; 
            $scope.kalafcheStores = [];
            $scope.selectedKalafcheStore = {};
            
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
                maxDate: new Date(2020, 5, 22),
                minDate: $scope.startDate,
                startingDay: 1,
                showWeeks: false
            };

            getAllRealStores(); 
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

        $scope.searchSales = function() {
            getSales();         
        }

        function getSales() {
            SaleService.searchSales($scope.startDateMilliseconds, $scope.endDateMilliseconds, $scope.selectedKalafcheStore.identifiers).then(function(response) {
                $scope.sales = response.sales;
            });           
        }

        function getSaleItems(sale) {
            SaleService.getSaleItems(sale.id).then(function(response) {
                sale.saleItems = response;
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

        $scope.changeKalafcheStore = function() {
            $scope.searchSales();
        };

        function getAllRealStores() {
            KalafcheStoreService.getAllRealStores().then(function(response) {
                $scope.kalafcheStores = response;
                $scope.selectedKalafcheStore = KalafcheStoreService.getRealSelectedStore($scope.kalafcheStores, $scope.isAdmin());
                getSales();
            });

        };

        $scope.getSaleTimestamp = function(sale) {
            return ApplicationService.convertEpochToDate(sale.saleTimestamp)
        };

        $scope.getTotalSum = function() {
            var totalSum = 0;

            for (var i = 0; i < $scope.sales.length; i++) {
                var currSale = $scope.sales[i];
                if ((!$scope.selectedKalafcheStore.id || currSale.kalafcheStoreId === $scope.selectedKalafcheStore.id) && currSale.saleTimestamp >= $scope.startDateMilliseconds && currSale.saleTimestamp <= $scope.endDateMilliseconds) {
                    totalSum += currSale.salePrice;
                }
            }

            return Math.round(totalSum * 100) / 100;
        };

        $scope.isTotalSumRowVisible = function() {
            if ($scope.salesPerPage * $scope.currentPage >= $scope.sales.length) {
                return true;
            } else {
                return false;
            }
        };

        $scope.resetCurrentPage = function() {
            $scope.currentPage = 1;
        };

        $scope.expand = function(sale) {
            getSaleItems(sale);
            sale.expanded = !sale.expanded;
        };

        $scope.isAdmin = function() {
            return AuthService.isAdmin();
        }

    };

