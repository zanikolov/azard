'use strict';

angular.module('kalafcheFrontendApp')
    .controller('PartnerController', function($scope, PartnerService, PartnerStoreService, ApplicationService) {
        
       init();

        function init() {
            $scope.newPartner = {};
            $scope.partnerStores = [];
            $scope.deviceTypes = [];
            $scope.partners = [];
            $scope.isErrorMessageVisible = false;  
            $scope.errorMessage = ""; 
            $scope.currentPage = 1; 
            $scope.partnersPerPage = 10; 

            getAllPartnerStores();
            //getAllDeviceTypes();
            getAllPartners();
        }

        $scope.submitPartner = function() {
            if (ApplicationService.validateDuplication($scope.newPartner.name, $scope.partners)) {           
                PartnerService.submitPartner($scope.newPartner).then(function(response) {
                    $scope.partners.push($scope.newPartner);
                    resetPartnerState();
                    $scope.isErrorMessageVisible = false; 
                    $scope.partnerForm.$setPristine();
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Има въведен партньор с това име";
            }
        };

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        function getAllPartnerStores() {
            PartnerStoreService.getAllPartnerStores().then(function(response) {
                $scope.partnerStores = response;
            });

        };

        // function getAllDeviceTypes() {
        //     DeviceTypeService.getAllDeviceTypes().then(function(response) {
        //         $scope.deviceTypes = response;
        //     });

        // };

        function getAllPartners() {
            PartnerService.getAllPartners().then(function(response) {
                $scope.partners = response;
            });

        };

        function resetPartnerState() {
            $scope.newPartner = {};
        }
    });