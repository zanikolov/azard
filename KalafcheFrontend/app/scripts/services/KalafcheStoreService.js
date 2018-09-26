'use strict';

angular.module('kalafcheFrontendApp')
	.service('KalafcheStoreService', function($http, Environment, SessionService) {
		angular.extend(this, {
            getAllKalafcheStores: getAllKalafcheStores,
            getSelectedKalafcheStore: getSelectedKalafcheStore,
            submitKalafcheStore: submitKalafcheStore,
            getAllRealStores: getAllRealStores,
            getRealSelectedStore: getRealSelectedStore
		});

        function getAllKalafcheStores() {   
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/kalafcheStore/getAllKalafcheStores')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        };

        function submitKalafcheStore(kalafcheStore) {
            return $http.post(Environment.apiEndpoint + '/KalafcheBackend/service/kalafcheStore', kalafcheStore)
                .then(
                    function(response) {
                        console.log(response);
                    }
                )
        };

        function getSelectedKalafcheStore(stores, isAdmin) {
            if (isAdmin) {
                return stores[0];
            } else {
                for (var i = 0; i < stores.length; i++) {
                    var store = stores[i];

                    if (store.id == SessionService.currentUser.employeeKalafcheStoreId) {
                        return store;
                    }
                }
            }
        };

        function getAllRealStores() {
            return $http.get(Environment.apiEndpoint + '/KalafcheBackend/service/kalafcheStore/getStores')
                .then(
                    function(response) {
                        return response.data
                    }
                ) ;
        };

        function getRealSelectedStore(stores, isAdmin) {
            if (isAdmin) {
                return stores[0];
            } else {
                for (var i = 0; i < stores.length; i++) {
                    var store = stores[i];

                    if (store.identifiers == SessionService.currentUser.employeeKalafcheStoreId) {
                        return store;
                    }
                }
            }
        };

	});

