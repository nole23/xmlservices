/**
 * 
 */

angular.module('xmlClientApp')
	.factory('ActResource', ['Restangular', '_', function(Restangular, _) {
		'use stict';
		
		
		var retVal = {};
		
		var lista = [];
		
		retVal.getUsvojeni = function(id) {
			var link = 'act/collection/'+id;
			return Restangular.all(link).getList().then(function(entries) {
				lista = entries;
				return lista;
			});
		};
		
		
		return retVal;
		
	}]);