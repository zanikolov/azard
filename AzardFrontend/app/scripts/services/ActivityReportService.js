'use strict';

angular.module('kalafcheFrontendApp')
	.service('ActivityReportService', function($http, Environment) {
		angular.extend(this, {
			searchActivities: searchActivities
		});

        function searchActivities(dateMillis) { 
            var params = {"params" : {"dateMillis": dateMillis}};

            return $http.get(Environment.apiEndpoint + '/AzardBackend/service/employee/firstLoginForDate', params)
                .then(
                    function(response) {
                        return response.data
                    }
                );
        }
	});