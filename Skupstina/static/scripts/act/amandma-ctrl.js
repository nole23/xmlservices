'use strict';

angular.module('xmlClientApp')
	.controller('AddAmandmanCtrl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', 'ActResource', 
	   function($scope, $uibModal, $log, _, $routeParams, $window, ActResource) {
		
		var id = $routeParams.id;
		console.log("amadnami"+id);
		var nesto = [];
		
		ActResource.getAct(id).then(function(items) {
			$scope.dokument = items;
			
			console.log(items);
			
			$scope.amandman = {
					
					idAkta: items.id,
					linkAkta: id,
					Korisnik: items.korisnik,
					odobreno: false,
					dopunaIzmena: false,
					sluzbeniListAmandmana: {
						brojListaAkta: items.sluzbeniList.broj_lista,
						idAkta: '',
						mestoDatum: {
							mesto: items.sluzbeniList.mestoDatum.mesto,
							datum: items.sluzbeniList.mestoDatum.datum
						}
					},
					dopunaZakonaAmandmana: {
						dopunaIzmena: false,
						glava: [{
							id: 1,
							podaciGlave: {
								naslovGlave: '',
								broj_glave: '1'
							},
							podnaslovGlave: '',
							clan: [{
								id: 1,
								podaciClana: {
									naslovClana: '',
									brojClana: '1'
								},
								opis: ''
							}]
						}]
					}
				}
				
				
				$scope.addNewGlava = function(glId) {

					//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;
					
					$scope.amandman.dopunaZakonaAmandmana.glava.push({'id':glId+1, podnaslovGlave:'', 
						podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
							podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]});
					
					console.log($scope.amandman);
				};
				
				$scope.addNewClan = function(glId, clId) {

					//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1].length+1;
					$scope.amandman.dopunaZakonaAmandmana.glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
					console.log($scope.amandman.dopunaZakonaAmandmana.glava[glId-1].clan[clId-1]);
				};
				
				$scope.addAmandman = function () {
					
					console.log($scope.amandman);
					ActResource.saveAmandman($scope.amandman, callBack);
				}
				
				function callBack(success) {
					if(success == 200){
						$location.path('/amandman/proposed');
					} else if(success == 400) {
						console.log('ne radi nesto dobro');
					}
				}
	    })
	    
	    
		

	    
	}])