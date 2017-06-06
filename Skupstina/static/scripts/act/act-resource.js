/**
 * 
 */

angular.module('xmlClientApp')
	.factory('ActResource', ['Restangular', '_', function(Restangular, _) {
		'use stict';
		
		
		var retVal = {};
		
		var lista = [];
		var akt = [];
		
		retVal.getUsvojeni = function(id) {
			var link = 'act/collection/'+id;
			return Restangular.all(link).getList().then(function(entries) {
				lista = entries;
				return lista;
			});
		};
		
		retVal.getAct = function(id) {
			var link = 'act/find/'+id;
			return Restangular.one(link).get().then(function(entries) {
				akt = entries;
				return akt;
			});
		};
		
		
		return retVal;
		
	}]);