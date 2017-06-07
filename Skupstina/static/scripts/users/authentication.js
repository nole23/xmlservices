
(function () {
	angular
		.module('xmlClientApp')
		.factory('LoginResources', Service);
	
	function Service($http, $localStorage, $log, $window) {
		
		var service = {};
		
		service.login = login;
		
		return service;
		
		function login(user, callback) {
			$http.post('http://localhost:8080/api/user/login', user)
				.success(function(response) {
					if(response.jwt) {
						var currentUser = {}
					} else {
						callback(response);
					}
				});
		}
	}
})