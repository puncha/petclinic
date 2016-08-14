"use strict";

angular.module('pet').service('Pet', ['$resource', petService]);
angular.module('pet').service('PetType', ['$resource', petTypeService]);


function petService($resource) {
  return $resource('/api/pets/:id');
}

function petTypeService($resource) {
  return $resource('/api/petTypes/:id')
}
