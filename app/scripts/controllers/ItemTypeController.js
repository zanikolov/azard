'use strict';

angular.module('kalafcheFrontendApp')
    .controller('ItemTypeController', function($scope, ItemTypeService) {
        $scope.itemType = {};

        $scope.submitItemType = function() {
            ItemTypeService.submitItemType($scope.itemType);
            resetItemTypeState();
        };

        function resetItemTypeState() {
            $scope.itemType = {};
        }

        $scope.getAllItemTypes = function() {
            ItemTypeService.getAllItemTypes();
        };
    });