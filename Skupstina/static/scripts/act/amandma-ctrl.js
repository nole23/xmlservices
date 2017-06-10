'use strict';

angular.module('xmlClientApp')
	.controller('AddAmandmanCtrl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', '$location', 'ActResource', 
	   function($scope, $uibModal, $log, _, $routeParams, $window, $location, ActResource) {
		
		var id = $routeParams.id;
		console.log("amadnami"+id);
		var nesto = [];
		
		ActResource.getAct(id).then(function(items) {
			$scope.dokument = items;
			
			console.log(items.korisnik);
			
			$scope.amandman = {
					naslovAkta: items.naslov,
					idAkta: items.id,
					linkAkta: id,
					korisnik: items.korisnik,
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
					ActResource.saveAmandman($scope.amandman) .then(function (result) {
						if(result.message){
							$location.path('/amandman/proposed');
						} else if(success == 400) {
							console.log('ne radi nesto dobro');
						}
					})
				}
				
	    })
	}])
	
	.controller('AmandmanCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', 'ActResource', 
	   function($scope, $uibModal, $log, _, $routeParams, $window, ActResource) {
		
		$scope.list = [];
		var id = $routeParams.accept;
		var tip = 'amandman';
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
	            ActResource.converteAmandman(res[0], tip).then(function (result) {
	                var file = new Blob([result], {type: 'application/html'});
	                var fileURL = window.URL.createObjectURL(file);
	                a.href = fileURL;
	                a.download = fileName;
	                a.click();
	            })
			} else if(tip == 'PDF') {
				console.log("doradi");
			} else {
				console.log("doradi");
			}
		}
	    
	}])
	.controller('ReadAmandanCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', 'ActResource', 
	   function($scope, $uibModal, $log, _, $routeParams, $window, ActResource) {
		
		$scope.list = [];
		var id = $routeParams.id;
		var res = id.split(".");
		
		
		ActResource.getAmandman(res[0]).then(function(items) {
			$scope.list = items;
			$scope.xml = res[0];
			console.log("amandman "+JSON.stringify(items));
	    })
	    
	    $scope.acceptAmandman = function () {
			ActResource.getVote(res[0], 'accept', 'amandman').then(function(items) {
				if(items.error != null) {
					
					$scope.error = items.error;
				} else if(items.message != null) {
					console.log(items.vote);
					$scope.vote = items.vote;
					$scope.message = items.message;
				}
			})
		}
		
		$scope.deleteAmandman = function() {
			
			ActResource.getDelteAmandman(res[0]).then(function(items) {
				$scope.delete = true;
				window.location = '#/amandman/proposed';
		    });
		}
	    
	}])
	