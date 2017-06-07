
'use strict';

angular.module('xmlClientApp')
	.controller('LoginCtrl', ['$scope', '$localStorage', '$http', '$window', '$log', '_', '$rootScope', 'LoginResources', 
	    function($scope, $localStorage, $http, $window, $log, _, $rootScope, LoginResources) {
			
		$scope.isLogin = false;
		$scope.user = {};
		
		$scope.login = function() {
			LoginResources.login($scope.user, loginCbck);
		};
		
		function loginCbck(success) {
			if(success.error != null) {
				$scope.messafeLogin = success.error;
			} else if(success.error == null) {
				window.location = '#/';
			}
		}
		}])