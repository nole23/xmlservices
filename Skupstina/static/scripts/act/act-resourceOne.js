(function () {
	angular
		.module('xmlClientApp')
		.factory('Act', Service);
	
	function Service($http, $localStorage, $log, $window, $resource){
		var service = {};
		
		service.saveAct = saveAct;
		
		return service;
		
		
		function saveAct(akt, callBack) {
			
			$http.post('http://localhost:8080/api/act/add', akt)
				.success(function(response) {
					callBack(response);
				});
			
		}
	};
})();