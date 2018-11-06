'use strict';

angular.module('kalafcheFrontendApp')
    .service('ServerValidationService', function() {
        angular.extend(this, {
            processServerErrors: processServerErrors
        });

        function processServerErrors(errorResponse, formController) {
            console.log(errorResponse.status);
            if (errorResponse.status == 409 || errorResponse.status == 400 || errorResponse.status == 404) {
                    // if (errorResponse.data.non_field_errors) {
                    //     formController.$setValidity('serverError', false);
                    //     formController.$setSubmitted();
                    // }
                Object.getOwnPropertyNames(errorResponse.data.errors).forEach(function (field) {
                    if (angular.isObject(formController[field]) &&
                        formController[field].hasOwnProperty('$modelValue')) {
                        console.log(formController[field]);
                        formController[field].$setValidity('serverError', false);
                        formController[field].$setTouched();
                    } else {
                        console.error('Unknown server error ${field}.');
                    }
                });
            } else {
                console.error('Unknown server error response.');
            }
        }

    });