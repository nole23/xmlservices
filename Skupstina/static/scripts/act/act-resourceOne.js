(function () {
	angular
		.module('xmlClientApp')
		.factory('Act', Service);
	function Service($http, $localStorage, $log, $window, $resource){
		var Act = $resource('/act', {docId: '@docId'});
		return Act;
	};
})();