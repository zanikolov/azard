'use strict';

angular.module('kalafcheFrontendApp')
    .controller('PartnerStoreController', function($scope, PartnerStoreService, ApplicationService) {
        
        init();

        function init() {
            $scope.newPartnerStore = {};
            $scope.partnerStores = [];
            $scope.isErrorMessageVisible = false;  
            $scope.errorMessage = "";  

            getAllPartnerStores();
        }

        $scope.submitPartnerStore = function() {
            PartnerStoreService.submitPartnerStore($scope.newPartnerStore).then(function(response) {
                $scope.partnerStores.push($scope.newPartnerStore);
                resetPartnerStoreState();
                $scope.partnerStoreForm.$setPristine();
            });
        };

        function resetPartnerStoreState() {
            $scope.newPartnerStore = {};
        }

        function getAllPartnerStores() {
            PartnerStoreService.getAllPartnerStores().then(function(response) {
                $scope.partnerStores = response;
            });
        };
    });