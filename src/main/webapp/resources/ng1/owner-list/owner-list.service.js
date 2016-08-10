"use strict";

angular
  .module('ownerList')
  .service('OwnersService', ['$resource', function($resource) {
    return $resource('/api/owners/:id');
  }]);