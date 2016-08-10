"use strict";

angular
  .module('ngPetClinicApp', [
    'ui.router',
    'angular-loading-bar',
    'ownerList',
    'petList'
  ])
  .config(['toastyConfigProvider', function(toastyConfigProvider) {
    toastyConfigProvider.setConfig({
      clickToClose: true, position: 'top-right', theme: 'bootstrap'
    });
  }])
  .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('welcome', {
        url: '/welcome',
        templateUrl: 'welcome.html'
      })
      .state('ownerList', {
        url: '/ownerList',
        templateUrl: 'owner-list.html'
      })
      .state('petList', {
        url: '/petList',
        templateUrl: 'pet-list.html'
      });
  }]);

