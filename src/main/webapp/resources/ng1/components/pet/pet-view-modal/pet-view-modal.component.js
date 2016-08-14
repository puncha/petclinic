"use strict";

angular.module('pet').component('petViewModal', {
  bindings: {
    petId: '@',
    visibility: '<',
    onVisibilityChanged: '&',
  },
  templateUrl: 'components/pet/pet-view-modal/pet-view-modal.tpl.html',
  controller: ['$scope', 'Pet', 'toaster', petViewModalController]
});

function petViewModalController($scope, Pet, toaster) {
  this.pet = null;

  this.$onInit = function() {
    $("#petViewModal").on('hidden.bs.modal', ()=> {
      $scope.$apply(()=>this.notifyVisibilityChanged(false));
    });
  };

  this.notifyVisibilityChanged = function(visibility) {
    if(this.onVisibilityChanged) this.onVisibilityChanged({visibility: visibility});
  };

  this.$onChanges = function(/*changedObjects*/) {
    if(this.visibility && this.petId)
      this.viewPet();
  };

  this.getPet = function() {
    return new Promise((resolve, reject)=> {
      if(!this.petId) {
        toaster.error('Fetch pet failed', 'ID of pet is missing.');
        return reject("Parameter petId is missing.");
      }
      Pet.get({id: this.petId},
        (pet)=> {
          this.pet = pet;
          toaster.success(`Fetch pet #${this.petId} for view`);
          resolve(pet);
        },
        (error)=> {
          toaster.error("Fetch pet #${this.petId} failed", error);
          reject(error);
        });
    });
  };

  this.viewPet = function viewPet() {
    return this.getPet().then(
      ()=>$('#petViewModal').modal('show'),
      ()=>this.notifyVisibilityChanged(false)
    )
  };
}
