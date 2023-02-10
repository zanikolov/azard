'use strict';

angular.module('kalafcheFrontendApp')
    .controller('DeviceTypeController', function($scope, DeviceTypeService, ApplicationService) {

        init();

        function init() {
            $scope.deviceType = {};
            $scope.deviceTypes = [];
            $scope.isErrorMessageVisible = false;  
            $scope.errorMessage = "";   

            getAllDeviceTypes();
        }

        $scope.submitDeviceType = function() {
            if (ApplicationService.validateDuplication($scope.deviceType.name, $scope.deviceTypes)) {
                DeviceTypeService.submitDeviceType($scope.deviceType).then(function(response) {
                    $scope.deviceTypes.push($scope.deviceType);
                    resetDeviceTypeState();
                    $scope.isErrorMessageVisible = false; 
                    $scope.deviceTypeForm.$setPristine();
                });
            } else {
                $scope.isErrorMessageVisible = true;
                $scope.errorMessage = "Има въведен тип устройство с това име";
            }

        };
        
        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        function getAllDeviceTypes() {
            DeviceTypeService.getAllDeviceTypes().then(function(response) {
                $scope.deviceTypes = response;
            });
        };

        function resetDeviceTypeState() {
            $scope.deviceType = {};
        }
    });