'use strict';

angular.module('kalafcheFrontendApp')
	.service('DeviceTypeService', function($http) {
		angular.extend(this, {
			submitDeviceType: submitDeviceType,
            getAllDeviceTypes: getAllDeviceTypes
		});

    	function submitDeviceType(type) {	
			return $http.post('http://localhost:8080/KalafcheBackend/service/deviceType/insertDeviceType', type)
            	.then(
                	function(response) {
                    	console.log(response);
                	}
            	) 
    	}

        function getAllDeviceTypes() {   
            return $http.get('http://localhost:8080/KalafcheBackend/service/deviceType/getAllDeviceTypes')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        }
	});