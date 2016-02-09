'use strict';

angular.module('kalafcheFrontendApp')
	.controller('SearchController', function ($scope) {
	    $scope.showModal = false;
	    $scope.toggleModal = function(){
	        $scope.showModal = !$scope.showModal;
	    };
	});