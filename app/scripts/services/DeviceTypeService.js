'use strict';

angular.module('kalafcheFrontendApp')
	.service('DeviceTypeService', function($http, Environment) {
		angular.extend(this, {
			submitDeviceType: submitDeviceType,
            getAllDeviceTypes: getAllDeviceTypes
		});

    	function submitDeviceType(type) {	
			return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/deviceType/insertDeviceType', type)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	) 
    	}

        function getAllDeviceTypes() {   
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/deviceType/getAllDeviceTypes')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});