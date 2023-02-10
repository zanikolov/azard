'use strict';

angular.module('kalafcheFrontendApp')
	.service('StoreService', function($http, Environment, SessionService) {
		angular.extend(this, {
            getAllStores: getAllStores,
            getSelectedStore: getSelectedStore,
            submitStore: submitStore,
            getRealSelectedStore: getRealSelectedStore
		});

        function getAllStores() {   
            return $http.get(Environment.apiEndpoint + '/AzardBackend/store/entities')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        };

        function submitStore(store) { 
            if (store.id) {
                return $http.post(Environment.apiEndpoint + '/AzardBackend/store', store)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            } else {
                return $http.put(Environment.apiEndpoint + '/AzardBackend/store', store)
                    .then(
                        function(response) {
                            return response.data;
                        }
                    )
            }
        };

        function getSelectedStore(stores, isAdmin) {
            if (isAdmin) {
                return stores[0];
            } else {
                for (var i = 0; i < stores.length; i++) {
                    var store = stores[i];

                    if (store.id == SessionService.currentUser.employeeStoreId) {
                        return store;
                    }
                }
            }
        };

        function getRealSelectedStore(stores, isAdmin) {
            if (isAdmin) {
                return stores[0];
            } else {
                for (var i = 0; i < stores.length; i++) {
                    var store = stores[i];

                    if (store.identifiers == SessionService.currentUser.employeeStoreId) {
                        return store;
                    }
                }
            }
        };

	});

