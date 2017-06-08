'use strict';

angular.module('xmlClientApp')
	.controller('ActCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', 'ActResource', 
	   function($scope, $uibModal, $log, _, $routeParams, $window, ActResource) {
		
		$scope.list = [];
		var id = $routeParams.accept;
		var tip = $routeParams.tip;
		console.log(id + ' | ' + tip);
			
		ActResource.getUsvojeni(id, tip).then(function(items) {
			$scope.name = id;
			$scope.tip = tip;
			$scope.list = items;
	    })
	    
	    $scope.converte = function(id, tip) {
			var res = id.split(".");
			if(tip == 'HTML') {
				var fileName = id+".html";
	            var a = document.createElement("a");
	            document.body.appendChild(a);
	            a.style = "display: none";
	            ActResource.converte(res[0], tip).then(function (result) {
	                var file = new Blob([result], {type: 'application/html'});
	                var fileURL = window.URL.createObjectURL(file);
	                a.href = fileURL;
	                a.download = fileName;
	                a.click();
	            });
			} else if(tip == 'PDF') {
				console.log('jos nije realizovano');
			} else if(tip == 'RDF') {
				console.log('jos nije realizovano');
			}
			
			
			
		}
	}])
	
	.controller('AddActCtrl', ['$scope', '$uibModal', '$log', '_', '$routeParams', 'Act', '$location',
	   function($scope, $uibModal, $log, _, $routeParams, Act, $location) {
		
		
		
		
		
		$scope.dokument = {
				id:'1',
				korisnik:'Novica Nikolic',
				odobreno:false,
				naslov:'',
				sluzbeniList:{
					broj_lista:'',
					cena:'',
					mestoDatum:{
						mesto:'',
						datum:''
					}
				},
				propisi:[{
					id:'1',
					korisnik:'Novica Nikolic',
					naziv_propisa:'',
					preambula:'',
					uvodniDeo:[{
						glava:[{
							id:1,
							podnaslovGlave:'',
							podaciGlave:{
								broj_glave:'1',
								naslovGlave:''
							},
							clan:[{
								id:1,
								podaciClana:{
									brojClana:'1',
									naslovClana:''
								},
								opis:''
							}]
						}]
					}],
					glavniDeo:[{
						glava:[{
							id:1,
							podnaslovGlave:'',
							podaciGlave:{
								broj_glave:'1',
								naslovGlave:''
							},
							clan:[{
								id:1,
								podaciClana:{
									brojClana:'1',
									naslovClana:''
								},
								opis:''
							}]
						}]
					}],
					zavrsniDeo:[{
						glava:[{
							id:1,
							podnaslovGlave:'',
							podaciGlave:{
								broj_glave:'1',
								naslovGlave:''
							},
							clan:[{
								id:1,
								podaciClana:{
									brojClana:'1',
									naslovClana:''
								},
								opis:''
							}]
						}],
						potpis_presednika:'Novica Nikolic'
					}]
			}]
		};
		

		
		/**
		 * Funkcija pomocu koje dodajemo nove propise u nas dokument
		 */
		$scope.addNewPropis = function(id) {
			
			var newItemNo = $scope.dokument.propisi.length+1;
			$scope.dokument.propisi.push({'id':newItemNo, korisnik:'', naziv_propisa:'', preambula:'',
				uvodniDeo:[{glava:[{id:1, podnaslovGlave:'', podaciGlave:{broj_glave:'', naslovGlave:''},
					clan:[{id:1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]}]}]});
			console.log($scope.dokument.propisi);
		};
		
		/**
		 * Funkcija pomocu koje dodajemo zaglavnje za uvodni deo dokumenta
		 */
		$scope.addNewGlava = function(proId, glId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;
			$scope.dokument.propisi[proId-1].uvodniDeo[0].glava.push({'id':glId+1, podnaslovGlave:'', 
				podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
					podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]});
			
			console.log($scope.dokument.propisi[proId-1].uvodniDeo[0].glava);
		};
		
		/**
		 * Funkcija pomocu koje dodajemo novo zaglavlje za glavni deo
		 * dokumenta
		 */
		$scope.addNewGlavaGlavniDeo = function(proId, glId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;
			$scope.dokument.propisi[proId-1].glavniDeo[0].glava.push({'id':glId+1, podnaslovGlave:'', 
				podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
					podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]});
			
			console.log($scope.dokument.propisi[proId-1].glavniDeo[0].glava);
		};
		
		
		/**
		 * Funkcija pomocu koje dodajemo zaglavnje za zavrsni deo naseg
		 * dokumenta
		 */
		$scope.addNewGlavaZavrsniDeo = function(proId, glId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;
			$scope.dokument.propisi[proId-1].zavrsniDeo[0].glava.push({'id':glId+1, podnaslovGlave:'', 
				podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
					podaciClana:{brojClana:'1', naslovClana:''}, opis:''}], potpis_presednika:'Novica Nikolic'});
			
			console.log($scope.dokument.propisi[proId-1].zavrsniDeo[0].glava);
		};
		
		
		/**
		 * Funkcija pomocu koje dodajemo novi clan date glave u uvodnom delu
		 * dokumenta
		 */
		$scope.addNewClan = function(proId, glId, clId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1].length+1;
			$scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
			console.log($scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1]);
		};
		
		/**
		 * Funkcija pomocu koje dodajemo novi clan date glave za glavni deo
		 * dokumenta
		 */
		$scope.addNewClanGlavniDeo = function(proId, glId, clId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1].length+1;
			$scope.dokument.propisi[proId-1].glavniDeo[0].glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
			console.log($scope.dokument.propisi[proId-1].glavniDeo[0].glava[glId-1].clan[clId-1]);
		};
		
		/**
		 * Funkcija pomocu koje dodajemo novi clan date glave za zavrsni deo 
		 * dokumenta
		 */
		$scope.addNewClanZavrsniDeo = function(proId, glId, clId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1].length+1;
			$scope.dokument.propisi[proId-1].zavrsniDeo[0].glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
			console.log($scope.dokument.propisi[proId-1].zavrsniDeo[0].glava[glId-1].clan[clId-1]);
		};
		
		/**
		 * Funkcija kada se pozove treba da preko rest posalje na server kreirani
		 * JSON fajl sa svim podacima
		 */
		$scope.addAct = function() {
			//$scope.dokument.propisi = $scope.propisa;
			
			console.log(JSON.stringify($scope.dokument));
			Act.saveAct($scope.dokument, callBack);
		}
		
		function callBack(success) {
			if(success == 200){
				$location.path('/act/proposed');
			} else if(success == 400) {
				console.log('ne radi nesto dobro');
			}
		}
	}])
	.controller('ReadCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', 'ActResource', 
	   function($scope, $uibModal, $log, _, $routeParams, ActResource) {
		
		$scope.list = [];
		var id = $routeParams.id;
		var res = id.split(".");
		
		
		ActResource.getAct(res[0]).then(function(items) {
			$scope.list = items;
			$scope.xml = res[0];
	    })
	    
	    $scope.acceptAct = function() {
			ActResource.getVote(res[0], 'accept', 'act').then(function(items) {
				if(items.error != null) {
					
					$scope.error = items.error;
				} else if(items.message != null) {
					console.log(items.vote);
					$scope.vote = items.vote;
					$scope.message = items.message;
				}
		    });
		}
		
		$scope.againstAct = function() {
			ActResource.getVote(res[0], 'deccident', 'act').then(function(items) {
				console.log(items);
		    });
		}
		
		$scope.dopuna = function(id) {
			console.log(id);
		}
		
		
	}])
	
	
	