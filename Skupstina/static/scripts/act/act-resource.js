/**
 * 
 */

angular.module('xmlClientApp')
	.factory('ActResource', ['Restangular', '_', function(Restangular, _) {
		'use stict';
		
		
		var retVal = {};
		
		var message = [];
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
		
		retVal.getVote = function(id, vote, act) {
			var link = 'voting/';
			var glasanje = {};
			if(vote == 'accept') {
				glasanje = {
						tip: act,
						name: id,
						yn: true
				};
			}
			if(vote == 'deccident') {
				glasanje = {
						tip: act,
						name: id,
						yn: false
				};
			}
			
			return Restangular.all(link).post(glasanje).then(function(success) {
				message = success;
				return message;
			})
		};
		
		
		return retVal;
		
	}]);