'use strict';

/**
 * @ngdoc overview
 * @name studentsClientApp
 * @description
 * # studentsClientApp
 *
 * Main module of the application.
 */

angular
    .module('xmlClientApp', [
        'ngResource',
        'ngRoute',
        'ngCookies',
        'ngStorage',
        'restangular',
        'ui.bootstrap',
        'lodash'
    ])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
	        .when('/', {
	            templateUrl: 'views/home.html'
	        })
	        .when('/login', {
	            templateUrl: 'views/login.html',
	            controller: 'LoginCtrl',
	            controllerAs: 'registrovan'
	        })
	        .when('/:tip/:accept', {
	            templateUrl: 'views/list.html',
	            controller: 'ActCrtl',
	            controllerAs: 'acceptAct'
	        })
	        .when('/add/new/act', {
	            templateUrl: 'views/add.html',
	            controller: 'AddActCtrl',
	            controllerAs: 'addAct'
	        })
	        .when('/add/new/amandman', {
	            templateUrl: 'views/add.html'
	        })
	        .when('/read/proposed/:id', {
	            templateUrl: 'views/readAct.html',
	            controller: 'ReadCrtl',
	            controllerAs: 'readCtrl'
	        })
	        .when('/read/accepted/:id', {
	            templateUrl: 'views/readAcceptAct.html',
	            controller: 'ReadCrtl',
	            controllerAs: 'readCtrl'
	        })
	        .when('/read/accepted/:id', {
	            templateUrl: 'views/readAcceptAct.html',
	            controller: 'ReadCrtl',
	            controllerAs: 'readCtrl'
	        })
	        .otherwise({
                redirectTo: '/'
            });
        
    }])
    
    .run(['Restangular', '$log', '$rootScope', '$http', '$location', '$localStorage', 'LoginResources', function(Restangular, $log, $rootScope, $http, $location, $localStorage, LoginResources) {
        Restangular.setBaseUrl("api");
        Restangular.setErrorInterceptor(function(response) {
            if (response.status === 500) {
                $log.info("internal server error");
                return true; // greska je obradjena
            }
            return true; // greska nije obradjena
        });
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }
        $rootScope.logout = function () {
        	LoginResources.logout();
        }
       
        $rootScope.isLoggedIn = function () {
            if (LoginResources.getCurrentUser()){
              return true;
            }
            else{
              return false;
            }
        }

        
    }]);
