'use strict';

(function() {
	angular
		.module('kalafcheFrontendApp')
		.factory('loginService', loginService);

	loginService.$inject = [
		
	];

	function loginService(

	) {

		var errorsId = [];

		var service = {
			// authenticate: authenticate
		};

		return service;

		//////////////////////////////

		function authenticate(credentials) {
			sendCredentialsToBackend(credentials);
		}

		function sendCredentialsToBackend(credentials) {
			$http.post("", {
				headers: credentials
			}).then(onSuccessfulResponse, onErrorResponse);
		}

		function onSuccessfulResponse(response) {
			// var user = response.data.user;
				$location.path("/dashboard");
				if (typeof(Storage) !== undefined) {
					localStorage.setItem("username", user.id);
				} else {
					console.log("no local storage support");
				}
			
		}

		function onErrorResponse() {
			if (error.status === 401) {
				console.log("Invalid Credentials!");
			} 
			$location.path("/login");
		}
	}
})();
