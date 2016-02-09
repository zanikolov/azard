'use strict';

angular.module('kalafcheFrontendApp')
    .controller('DeviceTypeController', function($scope, DeviceTypeService) {
        $scope.deviceType = {};

        $scope.submitDeviceType = function() {
            DeviceTypeService.submitDeviceType($scope.deviceType);
            resetDeviceTypeState();
        };

        function resetDeviceTypeState() {
            $scope.deviceType = {};
        }

        $scope.getAllDeviceTypes = function() {
            DeviceTypeService.getAllDeviceTypes();
        };
    });