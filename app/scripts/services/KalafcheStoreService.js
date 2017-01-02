'use strict';

angular.module('kalafcheFrontendApp')
	.service('KalafcheStoreService', function($http, Environment, SessionService) {
		angular.extend(this, {
            getAllKalafcheStores: getAllKalafcheStores,
            getSelectedKalafcheStore: getSelectedKalafcheStore
		});

        function getAllKalafcheStores() {   
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/kalafcheStore/getAllKalafcheStores')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        };


        function getSelectedKalafcheStore(stores, isAdmin) {
            if (isAdmin) {
                return {};
            } else {
                for (var i = 0; i < stores.length; i++) {
                    var store = stores[i];

                    if (store.id == SessionService.currentUser.employeeKalafcheStoreId) {
                        return store;
                    }
                }
            }
        }
	});

