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
	            templateUrl: 'views/login.html'
	        })
	        .when('/act/:accept', {
	            templateUrl: 'views/list.html',
	            controller: 'ActCrtl',
	            controllerAs: 'acceptAct'
	        })
	        .when('/amandman/:accept', {
	            templateUrl: 'views/list.html'
	        })
	        .when('/act/:proposed', {
	            templateUrl: 'views/list.html',
	            controller: 'ActCrtl',
	            controllerAs: 'acceptAct'
	        })
	        .when('/amandman/:proposed', {
	            templateUrl: 'views/list.html'
	        })
	        .when('/add/act', {
	            templateUrl: 'views/add.html',
	            controller: 'AddActCtrl',
	            controllerAs: 'addAct'
	        })
	        .when('/add/amandman', {
	            templateUrl: 'views/add.html'
	        })
	        .when('/read/:name', {
	            templateUrl: 'views/readAct.html',
	            controller: 'ReadCrtl',
	            controllerAs: 'readCtrl'
	        })
	        .otherwise({
                redirectTo: '/'
            });
        
    }])
    
    .run(['Restangular', '$log', '$rootScope', '$http', '$location', '$localStorage', function(Restangular, $log, $rootScope, $http, $location, $localStorage) {
        Restangular.setBaseUrl("api");
        Restangular.setErrorInterceptor(function(response) {
            if (response.status === 500) {
                $log.info("internal server error");
                return true; // greska je obradjena
            }
            return true; // greska nije obradjena
        });
        
        
    }]);
