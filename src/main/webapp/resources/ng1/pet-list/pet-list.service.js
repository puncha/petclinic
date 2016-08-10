"use strict";

angular
  .module('petList')
  .service('PetsService', ['$resource', function($resource) {
    return $resource('/api/pets/:id');
  }])
  .service('PetTypesService', ['$resource', function($resource) {
    return $resource('/api/petTypes/:id')
  }])
;