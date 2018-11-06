'use strict';

angular.module('kalafcheFrontendApp')
    .directive('saleReportByItem', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/sale-report/by-item.html',
            controller: SaleItemReportController
        }
    });

    function SaleItemReportController($scope, ApplicationService, AuthService, SaleService, KalafcheStoreService, BrandService, ModelService) {

        init();

        function init() {
            $scope.currentPage = 1;  
            $scope.salesPerPage = 15;
            $scope.saleItems = []; 
            $scope.kalafcheStores = [];
            $scope.brands = [];
            $scope.models = [];
            $scope.selectedBrand = {};
            $scope.selectedModel = {};
            $scope.selectedKalafcheStore = {};
            $scope.productCode = "";
            
            $scope.dateFormat = 'dd-MMMM-yyyy';
            $scope.startDate = {};
            $scope.endDate = {};
            $scope.startDateMilliseconds = {};
            $scope.endDateMilliseconds = {};
            $scope.startDatePopup = {opened: false};
            $scope.endDatePopup = {opened: false};

            $scope.warehouseQuantity = 0;
            $scope.companyQuantity = 0;
            $scope.isQuantitiesVisible = false;

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
            getAllBrands();
            getAllDeviceModels();   
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

        function getAllBrands() {
            BrandService.getAllDeviceBrands().then(function(response) {
                $scope.brands = response;
            });
        };

        function getAllDeviceModels() {
            ModelService.getAllDeviceModels().then(function(response) {
                $scope.models = response; 
            });
        };


        $scope.searchSaleItems = function() {
            getSaleItems();         
        }

        function getSaleItems() {
            SaleService.searchSaleItems($scope.startDateMilliseconds, $scope.endDateMilliseconds, $scope.selectedKalafcheStore.identifiers,
                $scope.selectedBrand.id, $scope.selectedModel.id, $scope.productCode).then(function(response) {
                $scope.saleItems = response.saleItems;
                $scope.warehouseQuantity = response.warehouseQuantity;
                $scope.companyQuantity = response.companyQuantity;

                if ($scope.selectedModel.id && $scope.productCode) {
                    $scope.isQuantitiesVisible = true;
                } else {
                    $scope.isQuantitiesVisible = false;
                }
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
            $scope.searchSaleItems();
        };

        $scope.openStartDatePopup = function() {
            $scope.startDatePopup.opened = true;
        };

        $scope.openEndDatePopup = function() {
            $scope.endDatePopup.opened = true;
        };

        $scope.filterByPeriod = function () {
            return function predicateFunc(sale) {
                return sale.saleTimestamp >= $scope.startDateMilliseconds && sale.saleTimestamp <= $scope.endDateMilliseconds;
            };
        };

        function getAllRealStores() {
            KalafcheStoreService.getAllRealStores().then(function(response) {
                $scope.kalafcheStores = response;
                $scope.selectedKalafcheStore = KalafcheStoreService.getRealSelectedStore($scope.kalafcheStores, $scope.isAdmin());
                getSaleItems();
            });

        };

        $scope.getSaleTimestamp = function(sale) {
            var timeStamp = new Date(sale.saleTimestamp);

            var minutes = ApplicationService.getTwoDigitNumber(timeStamp.getMinutes());
            var hh = ApplicationService.getTwoDigitNumber(timeStamp.getHours())
            var dd = ApplicationService.getTwoDigitNumber(timeStamp.getDate());
            var mm = ApplicationService.getTwoDigitNumber(timeStamp.getMonth() + 1); //January is 0!
            var yyyy = timeStamp.getFullYear();

            return dd + "-" + mm + "-" + yyyy + " " + hh + ":" + minutes;
        };


        $scope.isTotalSumRowVisible = function() {
            if ($scope.saleItemsPerPage * $scope.currentPage >= $scope.saleItems.length) {
                return true;
            } else {
                return false;
            }
        };

        $scope.filterByProductCode = function() {
            var productCodesString = $scope.productCode;
            var productCodes = productCodesString.split(" ");
            return function predicateFunc(sale) {
                return productCodes.indexOf(sale.productCode) !== -1 ;
            };
        };

        $scope.resetCurrentPage = function() {
            $scope.currentPage = 1;
        };

        $scope.expandAll = function(expanded) {
            $scope.$broadcast('onExpandAll', {
                expanded: expanded
            });
        };

        $scope.expand = function(sale) {
            getSaleItems(sale);
            sale.expanded = !sale.expanded;
        };

        
        $scope.isAdmin = function() {
            return AuthService.isAdmin();
        }

    };

