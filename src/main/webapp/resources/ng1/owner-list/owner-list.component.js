"use strict";

angular
  .module('ownerList')
  .component('ownerList', {
    templateUrl: 'owner-list/owner-list.template.html',
    controller: ['OwnersService', 'toasty', function(OwnersService, toasty) {
      this.firstNameToSearch = '';
      this.owners = OwnersService.query();
      this.currentOwner = null;
      this.isViewMode = false;

      this.onSuccess = ()=>toasty.success("Done");
      this.onError = (error)=>toasty.error({
        title: 'Fail',
        msg: _.get(error, "data.message", _.get(error, "data", error))
      });

      this.displayNameForField = (field)=>
        _.capitalize(_.snakeCase(field).split('_').join(' '));

      this.search = function() {
        // Get list of owners without pets
        this.owners = OwnersService.query({
          firstName: this.firstNameToSearch
        }, null, null, this.onError);
      };

      this.view = function(owner) {
        // Fetch the owner with pets
        OwnersService.get({id: owner.id}, (owner)=> {
          this.isViewMode = true;
          this.currentOwner = owner;
          $('#ownerDetailModal').modal('show');
        }, this.onError);
      };

      this.edit = function(owner) {
        OwnersService.get({id: owner.id}, (owner)=> {
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
        OwnersService.save(queryParam, owner, ()=> {
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

    }]
  });