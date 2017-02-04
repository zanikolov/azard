'use strict';

(function() {
    angular
        .module('kalafcheFrontendApp')
        .factory('User', user);

    user.$inject = [];

    function user() {
        var User = function User(options) {
    //         if (!options || !options.hasOwnProperty('id') ||
    //             !options.hasOwnProperty('userName') ||
    //             !options.hasOwnProperty('password') ||
    //             !options.hasOwnProperty('firstName') ||
    //             !options.hasOwnProperty('lastName') ||
    //             !options.hasOwnProperty('roomId') ||
				// !options.hasOwnProperty('status') ||
				// !options.hasOwnProperty('role') ||
    //             !options.hasOwnProperty('favorite')) {
    //             throw 'Invalid options object';
    //         }

            angular.extend(this, {
                id: options.id,
                password: options.password,
                firstName: options.firstName,
                lastName: options.lastName,
				role: options.role,
            });
        };

        angular.extend(User.prototype, {
            getId: getId,
            getPassword: getPassword,
            getFirstName: getFirstName,
            getLastName: getLastName,
			getRole: getRole
        });

        return User;

        //////////////////////////////

        function getId() {
            return this.id;
        }

        function setPassword(password) {
            this.password = password;
        }

        function getPassword() {
            return this.password;
        }

        function setFirstName(firstName) {
            this.firstName = firstName;
        }

        function getFirstName() {
            return this.firstName;
        }

        function setLastName(lastName) {
            this.lastName = lastName;
        }

        function getLastName() {
            return this.lastName;
        }
    }
})();
