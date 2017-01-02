'use strict';

angular.module('kalafcheFrontendApp')
	.service('ApplicationService', function() {
		angular.extend(this, {
			validateDuplication: validateDuplication,
            validateItemCodeDuplication: validateItemCodeDuplication,
            getCurrentTimestamp: getCurrentTimestamp,
            getTwoDigitNumber: getTwoDigitNumber
		});

        function validateDuplication(valueForValidation, list) {  
            for (var i = 0; i < list.length; i++) {
                var current = list[i];

                if (valueForValidation === current.name) {
                    return false;
                }
            }

            return true;
        };

        function validateItemCodeDuplication(item, items) {
            for (var i = 0; i < items.length; i++) {
                var current = items[i];

                if (item.productCode === current.productCode) {
                    return false;
                }
            }

            return true;
        };

        function getCurrentTimestamp() { 
            var today = new Date();

            return today.getTime();         
        };

        function getTwoDigitNumber(number) { 
            if (number < 10) {
                number = "0" + number;
            }   

            return number;      
        };
	});