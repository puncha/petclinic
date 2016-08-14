"use strict";

angular.module('pet').component('petEditModal', {
  bindings: {
    petId: '@',
    visibility: '<',
    onVisibilityChanged: '&',
    onSaved: '&'
  },
  templateUrl: 'components/pet/pet-edit-modal/pet-edit-modal.tpl.html',
  controller: ['$scope', '$element', 'Pet', 'PetType', 'Owner', 'Visit',
    'toaster', petEditModalController]
});

function petEditModalController($scope, $element, Pet, PetType, Owner, Visit, toaster) {
  this.pet = null;
  this.owners = null;
  this.petTypes = null;
  this.modalDialog = null;

  this.$onInit = function() {
    this.modalDialog = $element.find(".modal");
    this.modalDialog.on('hidden.bs.modal', ()=> {
      $scope.$apply(()=>this.notifyVisibilityChanged(false));
    });
  };

  this.notifyVisibilityChanged = function(visibility) {
    if(this.onVisibilityChanged) this.onVisibilityChanged({visibility: visibility});
  };

  this.notifySaved = function() {
    if(this.onSaved) this.onSaved({pet: this.pet});
  };

  this.$onChanges = function(/*changedObjects*/) {
    if(this.visibility && this.petId)
      this.edit();
  };

  this.isNewPet = function() {
    return this.petId === "-1";
  };

  this.createPet = function() {
    return Object.create({id: "-1"});
  };

  this.formatPet = function(pet, petTypes, owners) {
    // Convert string to date to make AngularJs Validation happy
    if(pet.birthDate) pet.birthDate = new Date(pet.birthDate);
    // Reference the one in owner and petType list
    if(pet.owner) pet.owner = _.find(owners, (owner)=>pet.owner.id === owner.id);
    if(pet.type) pet.type = _.find(petTypes, (petType)=>pet.type.id === petType.id);
    return pet;
  };

  this.getPet = function() {
    return new Promise((resolve, reject)=> {
      if(!this.petId) {
        toaster.error('Fetch pet failed', 'ID of pet is missing.');
        return reject("Parameter petId is missing.");
      }

      let promises = Promise.all([
        this.isNewPet() ? this.createPet() : Pet.get({id: this.petId}).$promise,
        Owner.query().$promise,
        PetType.query().$promise
      ]);

      promises.then(
        (values)=> {
          this.owners = values[1];
          this.petTypes = values[2];
          this.pet = this.formatPet(values[0], this.petTypes, this.owners);
          if(this.isNewPet()) toaster.success(`Start creating a new pet`);
          else toaster.success(`Fetch pet #${this.petId} for edit`);
          resolve(this.pet);
        },
        (error)=> {
          if(this.isNewPet()) toaster.error(`Create new pet failed`);
          else toaster.error("Fetch pet failed", error);
          reject(error);
        });
    });
  };

  this.edit = function view() {
    return this.getPet().then(
      ()=>this.modalDialog.modal('show'),
      ()=>this.notifyVisibilityChanged(false)
    )
  };

  this.save = function() {
    let queryParam = this.isNewPet() ? null : {id: this.pet.id};

    // visits collection should be included
    let petCopy = _.omit(this.pet, 'visits');
    Pet.save(queryParam, petCopy,
      ()=> {
        this.modalDialog.modal('hide');
        toaster.success("Pet is saved");
        this.notifyVisibilityChanged(false);
        this.notifySaved();
      },
      (error)=> {
        toaster.error("Fetch pet failed", error);
      }
    );
  };


  ////////////////
  //  Visit CRUD
  ////////////////

  this.visitEditModal = {
    visibility: false,
    onVisibilityChanged: function(visibility) {
      this.visibility = visibility;
    },
    onSaved: (/*visit*/)=> {
      this.getPet();    // refresh self
      this.onSaved(this.pet); // refresh pet list
    }
  };

  this.createVisit = function createVisit() {
    _.assignIn(this.visitEditModal, {
      petId: this.petId,
      visibility: true
    });
  };

  this.deleteVisit = function deleteVisit(visit) {
    Visit.delete({petId: this.pet.id, id: visit.id},
      ()=> {
        _.remove(this.pet.visits, visit);
        toaster.success(`Visit #${visit.id} is deleted`);
      },
      (error)=> {
        toaster.error(`Delete Visit #${visit.id} failed`, error);
      });
  };
}
