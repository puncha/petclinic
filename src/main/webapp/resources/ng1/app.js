"use strict";

const dependencies = [
  'ui.router',
  'angular-loading-bar',
  'pet'
];

angular.module('ngPetClinicApp', dependencies).config([
  '$stateProvider', '$urlRouterProvider', appConfig
]);


function appConfig($stateProvider, $urlRouterProvider) {

  $urlRouterProvider
    .when('', '/welcome')
    .otherwise('/welcome');

  $stateProvider
    .state('welcome', {
      url: '/welcome',
      templateUrl: 'fragments/welcome.html'
    })
    .state('ownerList', {
      url: '/ownerList',
      templateUrl: 'fragments/owners.html'
    })
    .state('petList', {
      url: '/petList',
      templateUrl: 'fragments/pets.html'
    });
}
