'use strict';

(function() {
    angular
        .module('kalafcheFrontendApp')
        .controller('loginModalController', loginModalController);

    loginModalController.$inject = ['$scope'];

    function loginModalController($scope) {

        $scope.credentials = {};

        angular.extend(this, {
            login: login,
        });

        console.log("Controller!!");

        function login() {
            // var headers = $scope.credentials ? {
            // 	authorization: "Basic " + window.btoa($scope.credentials.username + ":" + $scope.credentials.password)
            // } : {};
            // loginSrvc.authenticate(headers);
        }



        /*angular.element(document).ready(function() {
			if (typeof(Storage) !== undefined) {
				if (localStorage.username !== undefined) {
					$scope.credentials.username = localStorage.username;
					document.getElementById('password').focus();
				} else if (localStorage.username === undefined) {
					document.getElementById('username').focus();
				}

			} else {
				console.log("no local storage support");
			}
		});
*/
    }
})();
