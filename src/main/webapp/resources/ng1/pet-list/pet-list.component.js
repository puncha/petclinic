"use strict";

angular
  .module('petList')
  .component('petList', {
    templateUrl: 'pet-list/pet-list.template.html',
    controller: ['OwnersService', 'PetsService', 'PetTypesService', 'toasty',
      function(OwnersService, PetsService, PetTypesService, toasty) {

        // Common
        this.pets = null;
        this.onSuccess = ()=>toasty.success("Done");
        this.onError = (error)=>toasty.error({
          title: 'Fail',
          msg: _.get(error, "data.message", _.get(error, "data", error))
        });
        this.refresh = ()=>PetsService.query(null, null, (pets)=>this.pets = pets, this.onError);
        this.refresh();

        // View Related
        this.petToView = null;
        this.viewPet = (pet)=>
          Promise.resolve().then(()=> {
            return PetsService.get({id: pet.id}).$promise;
          }).then(()=> {
            this.petToView = pet;
            $('#petViewModal').modal('show');
          }).catch(this.onError);

        // Edit Related
        this.owners = null;
        this.petTypes = null;
        this.petToEdit = null;

        this.editPet = (pet)=>
          Promise.resolve().then(()=>
            Promise.all([
              pet.id === -1 ? pet : PetsService.get({id: pet.id}).$promise,
              OwnersService.query().$promise,
              PetTypesService.query().$promise
            ])
          ).then((values)=> {
            pet = this.petToEdit = values[0];
            this.owners = values[1];
            this.petTypes = values[2];
            // make AngularJs happy
            if(pet.birthDate) pet.birthDate = new Date(pet.birthDate);
            // Reference the one in owner and petType list
            if(pet.owner) pet.owner = _.find(this.owners, (owner)=>pet.owner.id === owner.id);
            if(pet.type) pet.type = _.find(this.petTypes, (petType)=>pet.type.id === petType.id);
            $('#petEditModal').modal('show');
          }).catch(this.onError);

        this.create = ()=>this.editPet({id: -1});

        this.save = (pet)=>
          Promise.resolve().then(()=> {
            let isUpdate = (pet.id !== -1);
            let queryParam = isUpdate ? {id: pet.id} : null;
            return PetsService.save(queryParam, pet, ()=> {
              $('#petEditModal').modal('hide');
              this.onSuccess();
              this.refresh();
            }).$promise;
          }).catch(this.onError);

        this.deletePet = (pet)=>
          Promise.resolve().then(()=>
            pet.$delete({id: -1})
          ).then(()=> {
            _.remove(this.pets, pet);
            this.onSuccess();
          }).catch(this.onError);

      }]
  });