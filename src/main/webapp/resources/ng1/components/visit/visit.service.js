"use strict";

angular.module('owner').service('Visit', ['$resource', visitService]);


function visitService($resource) {
  return $resource('/api/pets/:petId/visits/:id');
}