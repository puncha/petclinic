"use strict";

angular.module('visit').component('visitEditModal', {
  bindings: {
    petId: '@',
    visibility: '<',
    onVisibilityChanged: '&',
    onSaved: '&'
  },
  templateUrl: 'components/visit/visit-edit-modal/visit-edit-modal.tpl.html',
  controller: ['$scope', '$element', 'Visit', 'toaster', visitEditModalController]
});

function visitEditModalController($scope, $element, Visit, toaster) {
  this.visit = null;
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
    if(this.onSaved) this.onSaved({visit: this.visit});
  };

  this.$onChanges = function(/*changedObjects*/) {
    if(this.visibility && this.petId)
      this.createVisit();
  };

  this.createVisit = function() {
    this.visit = {};
    this.modalDialog.modal('show').css("z-index", 1500);

  };

  this.save = function() {
    Visit.save({petId: this.petId}, this.visit,
      ()=> {
        this.modalDialog.modal('hide');
        toaster.success("Visit is saved");
        this.notifyVisibilityChanged(false);
        this.notifySaved();
      },
      (error)=> {
        toaster.error("Save visit failed", error);
      }
    );
  };
}
