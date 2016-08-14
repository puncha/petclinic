"use strict";

angular
  .module('owner')
  .component('ownerList', {
    templateUrl: 'components/owner/owner-list/owner-list.tpl.html',
    controller: ['Owner', 'toaster', ownerListController]
  });

function ownerListController(Owner, toaster) {
  this.firstNameToSearch = '';
  this.owners = Owner.query();
  this.currentOwner = null;
  this.isViewMode = false;

  this.onSuccess = function() {
    toaster.success("Done");
  };
  this.onError = function(error) {
    toaster.error('Fail', error);
  };

  this.displayNameForField = function(field) {
    _.capitalize(_.snakeCase(field).split('_').join(' '));
  };

  this.search = function() {
    // Get list of owners without pets
    this.owners = Owner.query({
      firstName: this.firstNameToSearch
    }, null, null, this.onError);
  };

  this.view = function(owner) {
    // Fetch the owner with pets
    Owner.get({id: owner.id}, (owner)=> {
      this.isViewMode = true;
      this.currentOwner = owner;
      $('#ownerDetailModal').modal('show');
    }, this.onError);
  };

  this.edit = function(owner) {
    Owner.get({id: owner.id}, (owner)=> {
      this.isViewMode = false;
      this.currentOwner = owner;
      $('#ownerDetailModal').modal('show');
    }, this.onError);
  };

  this.create = function() {
    this.currentOwner = {id: -1};
    this.isViewMode = false;
    $('#ownerDetailModal').modal('show');
  };

  this.save = function(owner) {
    let isUpdate = (owner.id !== -1);
    let queryParam = isUpdate ? {id: owner.id} : null;
    Owner.save(queryParam, owner, ()=> {
      $('#ownerDetailModal').modal('hide');
      this.onSuccess();
      this.search();
    }, this.onError);
  };

  this.delete = function(owner) {
    owner.$delete({id: owner.id}, ()=> {
      _.remove(this.owners, owner);
      this.onSuccess();
    }, this.onError);
  }

}