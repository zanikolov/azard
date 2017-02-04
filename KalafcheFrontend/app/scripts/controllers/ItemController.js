'use strict';

angular.module('kalafcheFrontendApp')
    .controller('ItemController', function($scope, ItemService, ApplicationService) {

        init();

        function init() {
            $scope.newItem = {};
            $scope.items = []; 
            $scope.selectedItem = {}; 
            $scope.isNewItemRowVisible = false;
            $scope.isNameErrorMessageVisible = false;
            $scope.isCodeErrorMessageVisible = false;
            $scope.nameErrorMessage = "Има въведен артикул с това име"; 
            $scope.codeErrorMessage = "Има въведен артикул с този код"; 
            $scope.currentPage = 1;

            getAllItems();
        }

        $scope.submitNewItem = function() {
            if (ItemService.validateItem($scope.newItem, $scope.items, $scope.itemFormTable.newItemFormRow)) {

                // var fd = new FormData();

                

                // var oBlob = new Blob(['test'], { type: "text/plain"});
                // fd.append("file", oBlob,'test.txt');

                // $scope.newItem.pic = fd;

                ItemService.submitItem($scope.newItem).then(function(response) {
                    var insertedItem = response;
                    insertedItem.purchasePrice = Math.round((insertedItem.purchasePrice * 1.956)*100)/100;
                    $scope.items.unshift(insertedItem);
                    $scope.resetNewItem();
                    $scope.itemFormTable.newItemFormRow.$setPristine();
                });
            }  
        };

        $scope.updateEdittedItem = function(index) {
            var itemsWithoutSelected = angular.copy($scope.items);
            itemsWithoutSelected.splice(index, 1);

            if (ItemService.validateItem($scope.selectedItem, itemsWithoutSelected, $scope.itemFormTable.editItemFormRow)) {
                ItemService.updateItem($scope.selectedItem).then(function(response) {
                    $scope.selectedItem.purchasePrice = Math.round(($scope.selectedItem.purchasePrice * 1.956)*100)/100;
                    $scope.items[index] = angular.copy($scope.selectedItem);
                    $scope.selectedItem = {};
                    $scope.itemFormTable.editItemFormRow.$setPristine();
                });
            }  
        };

        $scope.getTemplate = function(item) {
            if ($scope.isSuperAdmin() && item && item.id === $scope.selectedItem.id){
                return 'edit';
            } else {
                return 'display';
            }
        };

        $scope.addNewItemRow = function() {
            $scope.isNewItemRowVisible = true;
        };

        $scope.editItem = function (item) {
            $scope.selectedItem = angular.copy(item);
        };

        $scope.resetSelectedItem = function () {
            $scope.selectedItem = {};
        };

        $scope.resetErrorMessage = function() {
            $scope.isErrorMessageVisible = false;
        };

        $scope.resetNewItem = function() {
            $scope.isNewItemRowVisible = false;
            $scope.newItem = {};
        };

        function getAllItems() {
            ItemService.getAllItems().then(function(response) {
                $scope.items = response;
            });
        };

        function getAllItems() {
            ItemService.getAllItems().then(function(response) {
                $scope.items = response;
            });
        };
    });