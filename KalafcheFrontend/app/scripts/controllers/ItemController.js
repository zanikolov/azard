'use strict';

angular.module('kalafcheFrontendApp')

    .directive('item', function() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'views/partials/assortment/item.html',
            controller: ItemController
        }
    });

    function ItemController ($scope, ItemService, ApplicationService) {

    	init();

        function init() {
            $scope.items = [];
            $scope.currentPage = 1; 
            $scope.itemsPerPage = 20;
            $scope.selectedItem = {};

            getAllItems();
        }

        $scope.resetSelectedItem = function () {
            $scope.selectedItem = {};
        };

        function getAllItems() {
            ItemService.getAllItems().then(function(response) {
                $scope.items = response;
            });
        };

        $scope.editItem = function (item) {
            $scope.selectedItem = angular.copy(item);
        };

        $scope.$on('cancelEdit', function (event, data) {
            $scope.selectedItem = {};
        });

        $scope.$on('submitSuccess', function (event, data) {
            $scope.selectedItem = {};
            getAllItems();
        });

    };