"use strict";

angular.module('pet').component('petList', {
  templateUrl: 'components/pet/pet-list/pet-list.template.html',
  controller: ['Pet', 'toaster', petListController]
});


function petListController(Pet, toaster) {
  this.pets = null;

  this.refresh = ()=> {
    Pet.query(
      (pets)=> {
        this.pets = pets;
        toaster.success("List pets succeeded.")
      },
      ()=> {
        toaster.error("List pets failed", error);
      });
  };

  // Immediately fetch all pets
  this.refresh();

  this.petViewModal = {
    petId: null,
    visibility: false,
    onVisibilityChanged: function(visibility) {
      this.visibility = visibility;
    }
  };

  this.viewPet = function view(pet) {
    _.assignIn(this.petViewModal, {
      petId: pet.id,
      visibility: true
    });
  };

  this.petEditModal = {
    petId: null,
    visibility: false,
    onVisibilityChanged: function(visibility) {
      this.visibility = visibility;
    },
    onSaved: (pet)=>this.refresh()  // this === $ctrl
  };

  this.editPet = function view(pet) {
    _.assignIn(this.petEditModal, {
      petId: pet.id,
      visibility: true
    });
  };

  this.createPet = function create() {
    _.assignIn(this.petEditModal, {
      petId: -1,
      visibility: true
    });
  };

  this.deletePet = (pet)=> {
    Pet.delete({id: pet.id},
      ()=> {
        _.remove(this.pets, pet);
        toaster.success(`Pet #${pet.id} is deleted`);
      },
      (error)=> {
        toaster.error(`Delete pet #${pet.id} failed`, error);
      });
  };
}
