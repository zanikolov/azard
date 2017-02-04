'use strict';

angular.module('kalafcheFrontendApp')
	.constant('UserRoles', {
	  	all: '*',
	  	superAdmin: 'ROLE_SUPERADMIN',
  		admin: 'ROLE_ADMIN',
  		user: 'ROLE_USER'
	}
);