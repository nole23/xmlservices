
(function () {
	angular
		.module('xmlClientApp')
		.factory('LoginResources', Service);
	
	function Service($http, $localStorage, $log, $window) {
		
		var service = {};
		
		service.login = login;
		service.getCurrentUser = getCurrentUser;
		service.logout = logout;
		
		return service;
		
		function login(user, callBack) {
			$http.post('http://localhost:8080/api/user/login', user)
				.success(function (response) {
					if(response.error != null) {
						callBack('invalid');
					} else {
						
						var currentUser = {
								username: user.usernam,
								token: response.jwt
						};
						
						$localStorage.currentUser = currentUser;
						$http.defaults.headers.common.Authorization = response.jwt;
						
						callBack("login");
					}
				});
		}
		
		function getCurrentUser() {
            return $localStorage.currentUser;
        }
		
		function logout() {
            // uklonimo korisnika iz lokalnog skladišta
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
            $window.location = '#/login';
        }
	}
})();