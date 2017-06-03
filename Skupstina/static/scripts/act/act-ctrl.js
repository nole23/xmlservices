'use strict';

angular.module('xmlClientApp')
	.controller('ActCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', 'ActResource', 
	   function($scope, $uibModal, $log, _, $routeParams, ActResource) {
		
		$scope.list = [];
		var id = $routeParams.accept;
			
		ActResource.getUsvojeni(id).then(function(items) {
			$scope.name = id;
			$scope.list = items;
	    })
	}])
	.controller('AddActCtrl', ['$scope', '$uibModal', '$log', '_', '$routeParams', 'Act', '$location',
	   function($scope, $uibModal, $log, _, $routeParams, Act, $location) {
		
		$scope.sluzbeniList = [{broj_lista: '1'}];
		$scope.choices.clanoviU = [{id: 'clanU1'}];
		$scope.choices.clanoviG = [{id: 'clanG1'}];
		$scope.choices.clanoviZ = [{id: 'clanZ1'}];
		  
		$scope.addNewChoice = function() {
			var newItemNo = $scope.sluzbeniList.length+1;
			$scope.sluzbeniList.push({'broj_lista':'newItemNo});
		};
		
		$scope.addNewClan = function() {
			var newItemNo = $scope.choices.clanoviU.length+1;
			$scope.choices.clanoviU.push({'id':'clanU'+newItemNo});
		};
		
		$scope.addNewClanG = function() {
			var newItemNo = $scope.choices.clanoviG.length+1;
			$scope.choices.clanoviG.push({'id':'clanG'+newItemNo});
		};
		
		$scope.addNewClanZ = function() {
			var newItemNo = $scope.choices.clanoviZ.length+1;
			$scope.choices.clanoviZ.push({'id':'clanZ'+newItemNo});
		};
		
		$scope.addAct = function() {
			
			console.log($scope.choices);
			
		}
	}])
