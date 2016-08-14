"use strict";

angular.module('owner').service('Owner', ['$resource', ownerService]);


function ownerService($resource) {
  return $resource('/api/owners/:id');
}